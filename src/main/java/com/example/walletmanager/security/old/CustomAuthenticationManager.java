package com.example.walletmanager.security.old;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.walletmanager.entity.User;
import com.example.walletmanager.service.impl.UserServiceImpl;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {
 
    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{
        
        User user = userServiceImpl.findByEmail(authentication.getName());
        
        System.out.println(authentication.getCredentials().toString());
        System.out.println("Result : " + passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword()));
        
        if(!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())){
            throw new BadCredentialsException("Incorrect password");
        }
        return new UsernamePasswordAuthenticationToken(authentication.getName(), user.getPassword());

    }
}