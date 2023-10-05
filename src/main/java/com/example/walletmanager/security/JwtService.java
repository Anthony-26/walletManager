package com.example.walletmanager.security;

import java.security.Key;
import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.walletmanager.exception.CustomExceptions.JwtTokenInvalidException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    private final String SECRET_KEY;
    private final int EXPIRATION_TIME;

    public JwtService(@Value("${jwt.secret_key}") String secretKey, @Value("${jwt.expiration_time}") int expiration_time){
        this.SECRET_KEY = secretKey;
        this.EXPIRATION_TIME = expiration_time;
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        /***
         * Adding custom claims to the token 
         *  
         Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
         Map<String, Object> authoritiesMap = new HashMap<>();
            for(GrantedAuthority authority : authorities){
                authoritiesMap.put("Role", authority.getAuthority());
            }
         System.out.println(authoritiesMap);
         return generateToken(authoritiesMap, userDetails);
        */

        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                    .setClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        // try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        // } catch (SignatureException e) {
        //     throw new JwtTokenInvalidException("Wrong token signature", e.getCause());
        // }
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}