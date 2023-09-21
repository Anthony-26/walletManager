package com.example.walletmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.walletmanager.entity.Stock;
import com.example.walletmanager.entity.User;
import com.example.walletmanager.service.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final UserServiceImpl userServiceImpl;
    
    @PostMapping("/verifyJWT")
    public ResponseEntity<Boolean> verify(@RequestParam String token, @RequestBody User user){
        return ResponseEntity.ok(userServiceImpl.verify(token, user));
    }

    @GetMapping("/test")
    public ResponseEntity<HttpStatus> test(){
        Stock s = new Stock();
        System.out.println(s + " Stock");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
