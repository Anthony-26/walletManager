package com.example.walletmanager.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.walletmanager.entity.Portfolio;
import com.example.walletmanager.entity.Stock;
import com.example.walletmanager.entity.StockQuantity;
import com.example.walletmanager.service.impl.PortfolioServiceImpl;
import com.example.walletmanager.service.impl.StockQuantityServiceImpl;
import com.example.walletmanager.service.impl.StockServiceImpl;
import com.example.walletmanager.service.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stockQuantity")
@RequiredArgsConstructor
public class StockQuantityController {

    private final StockQuantityServiceImpl stockQuantityServiceImpl;
    private final StockServiceImpl stockServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private final PortfolioServiceImpl portfolioServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addStockQuantity(@RequestBody StockQuantity stockQuantity){
        stockQuantityServiceImpl.saveStockQuantity(stockQuantity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/addWithStockId")
    public ResponseEntity<HttpStatus> addStockQuantityWithStockId(
            @RequestParam String ticker,
            @RequestParam Long idPortfolio,
            @RequestBody Map<String, Integer> body,
            @RequestHeader("Authorization") String authHeader) {
                
        StockQuantity stockQuantity = new StockQuantity(
            stockServiceImpl.getStockByTicker(ticker), 
            portfolioServiceImpl.getPortfolioById(idPortfolio),
            body.get("quantity")
        );
        stockQuantityServiceImpl.saveStockQuantity(stockQuantity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/stockQuantity")
    public ResponseEntity<StockQuantity> getStockQuantity(@RequestParam Long id){
        return new ResponseEntity<>(stockQuantityServiceImpl.getStockQuantityById(id), HttpStatus.OK);
    }

}
