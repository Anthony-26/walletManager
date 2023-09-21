package com.example.walletmanager.controller;

import com.example.walletmanager.entity.User;
import com.example.walletmanager.service.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@RequestParam Long userId) {
        User user = userServiceImpl.findById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(@RequestBody User user) {
        User updatedUser = userServiceImpl.update(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userServiceImpl.deleteById(userId);
        return ResponseEntity.ok("Utilisateur supprimé avec succès");
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userServiceImpl.findAll();
        return ResponseEntity.ok(users);
    }
}
