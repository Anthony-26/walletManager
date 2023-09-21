package com.example.walletmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.walletmanager.entity.Portfolio;
import com.example.walletmanager.entity.User;
import com.example.walletmanager.repository.UserRepository;
import com.example.walletmanager.security.JwtService;
import com.example.walletmanager.service.impl.PortfolioServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {
    
    private final PortfolioServiceImpl portfolioServiceImpl;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/createPortfolio")
    public ResponseEntity<HttpStatus> createPortfolio(@RequestHeader("Authorization") String authHeader){
        String jwtToken = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwtToken);
        User user = userRepository.findByEmail(userEmail).get();
        portfolioServiceImpl.save(new Portfolio(user));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getPortfolio/{id}")
    public ResponseEntity<Portfolio> getPortfolio(@PathVariable Long id){
        return new ResponseEntity<>(portfolioServiceImpl.getPortfolioById(id), HttpStatus.OK);
    }
}
