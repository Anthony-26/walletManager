package com.example.walletmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.walletmanager.entity.Portfolio;
import com.example.walletmanager.entity.Stock;
import com.example.walletmanager.entity.User;
import com.example.walletmanager.service.impl.PortfolioServiceImpl;
import com.example.walletmanager.service.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final UserServiceImpl userServiceImpl;
    private final PortfolioServiceImpl portfolioServiceImpl;
    
    @PostMapping("/verifyJWT")
    public ResponseEntity<Boolean> verify(@RequestParam String token, @RequestBody User user){
        return ResponseEntity.ok(userServiceImpl.verify(token, user));
    }

    @GetMapping("/testFunction")
    public ResponseEntity<HttpStatus> testFunction(){
        Stock s = new Stock();
        System.out.println(s + " Stock");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/portfolios")
    public ResponseEntity<List<Portfolio>> getAllPortfolios(){
        return new ResponseEntity<List<Portfolio>>(portfolioServiceImpl.findAllPortfolios(), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userServiceImpl.findAll();
        return ResponseEntity.ok(users);
    }

}