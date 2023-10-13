package com.example.walletmanager.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.walletmanager.DTO.StockQuantityDTO;
import com.example.walletmanager.entity.Portfolio;
import com.example.walletmanager.entity.StockQuantity;
import com.example.walletmanager.exception.CustomExceptions.StockAlreadyExistingException;
import com.example.walletmanager.service.impl.PortfolioServiceImpl;
import com.example.walletmanager.service.impl.StockQuantityServiceImpl;
import com.example.walletmanager.service.impl.StockServiceImpl;
import com.example.walletmanager.service.impl.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stockQuantities")
@RequiredArgsConstructor
public class StockQuantityController {

    private final StockQuantityServiceImpl stockQuantityServiceImpl;
    private final StockServiceImpl stockServiceImpl;
    private final PortfolioServiceImpl portfolioServiceImpl;
    private final UserServiceImpl userServiceImpl;

    @PostMapping("/{portfolioId}/stocks/{ticker}/quantities")
    public ResponseEntity<HttpStatus> addStockQuantityWithStockTicker(
            @PathVariable String ticker,
            @PathVariable Long portfolioId,
            @Valid @RequestBody StockQuantityDTO stockQuantityDTO,
            Authentication authentication) {

        Portfolio portfolio = portfolioServiceImpl.findPortfolioById(portfolioId);
        userServiceImpl.isTheSameUser(authentication,
                portfolio.getUser().getId());

        if (portfolioServiceImpl.isStockQuantityExisting(portfolioId, ticker))
            throw new StockAlreadyExistingException("This stock is already in your portfolio");

        StockQuantity stockQuantity = new StockQuantity(
                stockServiceImpl.findStockByTicker(ticker),
                portfolio,
                stockQuantityDTO.getQuantity());
        portfolio.addTotalCurrentValue(stockQuantity.getValue());
        stockQuantityServiceImpl.saveStockQuantity(stockQuantity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{stockQuantityId}")
    public ResponseEntity<StockQuantity> updateStockQuantityValue(
            @PathVariable Long stockQuantityId,
            @Valid @RequestBody StockQuantityDTO newStockQuantityDTO,
            Authentication authentication) {

        StockQuantity stockQuantity = stockQuantityServiceImpl.findStockQuantityById(stockQuantityId);
        userServiceImpl.isTheSameUser(authentication,
                stockQuantity.getPortfolio().getUser().getId());

        BigDecimal oldValue = stockQuantity.getValue();
        stockQuantity.setQuantity(newStockQuantityDTO.getQuantity());

        Portfolio portfolio = portfolioServiceImpl.findPortfolioById(stockQuantity.getPortfolio().getId());
        portfolio.updateTotalCurrentValue(oldValue, stockQuantity.getValue());
        portfolioServiceImpl.savePortfolio(portfolio);
        
        stockQuantityServiceImpl.saveStockQuantity(stockQuantity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/stockQuantity")
    public ResponseEntity<StockQuantity> getStockQuantity(@RequestParam Long id) {
        return new ResponseEntity<>(stockQuantityServiceImpl.findStockQuantityById(id), HttpStatus.OK);
    }

}