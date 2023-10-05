package com.example.walletmanager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


import com.example.walletmanager.entity.Portfolio;
import com.example.walletmanager.service.impl.PortfolioServiceImpl;
import com.example.walletmanager.service.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {
    
    private final PortfolioServiceImpl portfolioServiceImpl;
    private final UserServiceImpl userServiceImpl;

    @PostMapping("/{userId}/portfolio")
    public ResponseEntity<HttpStatus> createEmptyPortfolio(
            @PathVariable Long userId,
            @RequestBody Map<String, String> body,
            Authentication authentication) {

        userServiceImpl.isTheSameUser(authentication, userId);
        portfolioServiceImpl.savePortfolio(new Portfolio(
                body.get("name"),
                userServiceImpl.findUserById(userId)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Portfolio>> getUserPortfolios(
            @PathVariable Long userId,
            Authentication authentication) {

        userServiceImpl.isTheSameUser(authentication, userId);
        return new ResponseEntity<>(portfolioServiceImpl.findPortfoliosByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/portfolio/{portfolioId}")
    public ResponseEntity<Portfolio> getOneUserPortfolio(
            @PathVariable Long userId,
            @PathVariable Long portfolioId,
            Authentication authentication) {

        userServiceImpl.isTheSameUser(authentication, userId);
        if (portfolioServiceImpl.findPortfolioById(portfolioId).getUser().getId() != userId)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(portfolioServiceImpl.findPortfolioById(portfolioId), HttpStatus.OK);
    }

}