package com.example.walletmanager.controller;

import com.example.walletmanager.entity.Portfolio;
import com.example.walletmanager.entity.User;
import com.example.walletmanager.service.impl.PortfolioServiceImpl;
import com.example.walletmanager.service.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final PortfolioServiceImpl portfolioServiceImpl;

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@RequestParam Long userId) {
        User user = userServiceImpl.findUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
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

    @PostMapping("/{userId}/portfolio")
    public ResponseEntity<HttpStatus> createPortfolio(@PathVariable Long userId){

        //name, user

        // portfolioServiceImpl.save(new Portfolio(user));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/portfolios")
    public ResponseEntity<Set<Portfolio>> getPortfolios(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long userId) {
        return new ResponseEntity<>(userServiceImpl.findUserById(userId).getPortfolios(), HttpStatus.OK);
    }
}
