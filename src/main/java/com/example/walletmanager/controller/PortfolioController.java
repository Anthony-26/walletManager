package com.example.walletmanager.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.walletmanager.entity.Portfolio;
import com.example.walletmanager.entity.StockQuantity;
import com.example.walletmanager.entity.User;
import com.example.walletmanager.repository.StockQuantityRepository;
import com.example.walletmanager.repository.StockRepository;
import com.example.walletmanager.repository.UserRepository;
import com.example.walletmanager.security.JwtService;
import com.example.walletmanager.service.impl.PortfolioServiceImpl;
import com.example.walletmanager.service.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {
    
    private final PortfolioServiceImpl portfolioServiceImpl;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;
    private final StockQuantityRepository stockQuantityRepository;
    private final UserServiceImpl userServiceImpl;

    @PostMapping("/portfolio")
    public ResponseEntity<HttpStatus> createPortfolio(@RequestHeader("Authorization") String authHeader){
        User user = userServiceImpl.getUserWithJwtToken(authHeader);
        portfolioServiceImpl.save(new Portfolio(user));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/addStocks")
    public ResponseEntity<HttpStatus> addStockQuantity(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam Long idPortfolio,
            @RequestBody StockQuantity stockQuantity) {

        
        

        User user = userServiceImpl.getUserWithJwtToken(authHeader);
        Portfolio portfolio = portfolioServiceImpl.getPortfolioById(idPortfolio);
        if (portfolio.getUser() != user) 
            return new ResponseEntity<HttpStatus>(HttpStatus.UNAUTHORIZED);
        // stockQuantity.setStock(stockRepository.findByTicker("AAPL"));
        stockRepository.save(stockQuantity.getStock());
        stockQuantity.setPortfolio(portfolio);
        stockQuantityRepository.save(stockQuantity);
        portfolio.addStockQuantity(stockQuantity);
        // stockQuantity.setPortfolio(portfolio);
        portfolioServiceImpl.save(portfolio);
        return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
    }


}