package com.example.walletmanager.security.old;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.walletmanager.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    public AuthenticationFilter(CustomAuthenticationManager customAuthenticationManager) {
        this.customAuthenticationManager = customAuthenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws RuntimeException{
        try{
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            return customAuthenticationManager.authenticate(authentication);
        } catch(IOException e){
            throw new RuntimeException();
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) 
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(failed.getMessage());
        response.getWriter().flush();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) 
            throws IOException, ServletException{
        String token = JWT.create()
            .withSubject(authResult.getName())
            .withExpiresAt(new Date(System.currentTimeMillis() + 7200000L))
            .sign(Algorithm.HMAC512("TEST_DEMO"));
        response.addHeader("Authorization", "Bearer " + token);
    }
    
}