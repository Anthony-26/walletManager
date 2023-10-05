package com.example.walletmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.walletmanager.entity.Stock;
import com.example.walletmanager.service.impl.StockServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockServiceImpl stockServiceImpl;

    @PostMapping("/stock")
    public ResponseEntity<HttpStatus> addStock(@RequestParam String ticker) {
        stockServiceImpl.saveStock(ticker);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{ticker}")
    public ResponseEntity<Stock> getStock(@PathVariable String ticker) {
        return new ResponseEntity<>(stockServiceImpl.findStockByTicker(ticker), HttpStatus.OK);
    }

}
