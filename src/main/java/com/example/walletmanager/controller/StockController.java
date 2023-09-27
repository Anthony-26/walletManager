package com.example.walletmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.walletmanager.entity.Stock;
import com.example.walletmanager.service.impl.StockServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {
    
    private final StockServiceImpl stockServiceImpl;

    @PostMapping("/stock")
    public ResponseEntity<HttpStatus> addStock(@RequestBody Stock stock){
        stockServiceImpl.saveStock(stock);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/stock")
    public ResponseEntity<Stock> getStock(@RequestParam String ticker){
        return new ResponseEntity<>(stockServiceImpl.getStockByTicker(ticker), HttpStatus.OK);
    }

}
