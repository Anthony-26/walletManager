package com.example.walletmanager.controller;

import com.example.walletmanager.entity.User;
import com.example.walletmanager.service.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getProfile(@PathVariable Long userId, Authentication authentication) {
        userServiceImpl.isTheSameUser(authentication, userId);
        User user = userServiceImpl.findUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<HttpStatus> updateProfile(@PathVariable Long userId, @RequestBody User updatedUser,
            Authentication authentication) {
        userServiceImpl.isTheSameUser(authentication, userId);
        userServiceImpl.updateUser(userId, updatedUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteProfile(@PathVariable Long userId, Authentication authentication) {
        userServiceImpl.isTheSameUser(authentication, userId);
        userServiceImpl.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}