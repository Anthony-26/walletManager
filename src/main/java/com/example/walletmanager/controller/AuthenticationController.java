package com.example.walletmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.walletmanager.entity.User;
import com.example.walletmanager.service.impl.UserServiceImpl;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/authenticate")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final UserServiceImpl userServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
        userServiceImpl.register(user);
        return ResponseEntity.ok("Succesfully registered");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try{
            return ResponseEntity.ok(userServiceImpl.authenticate(user.getEmail(), user.getPassword()));
        } catch(AccessDeniedException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch(AuthenticationException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

}