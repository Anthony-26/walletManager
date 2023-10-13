package com.example.walletmanager.service.impl;

import org.springframework.stereotype.Service;

import com.example.walletmanager.entity.StockQuantity;
import com.example.walletmanager.exception.CustomExceptions.StockQuantityNotFoundException;
import com.example.walletmanager.repository.StockQuantityRepository;
import com.example.walletmanager.service.StockQuantityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockQuantityServiceImpl implements StockQuantityService{

    private final StockQuantityRepository stockQuantityRepository;

    @Override
    public void saveStockQuantity(StockQuantity stockQuantity) {   
        stockQuantityRepository.save(stockQuantity);
    }

    @Override
    public StockQuantity findStockQuantityById(Long id) {
        return stockQuantityRepository.findById(id).orElseThrow(() -> new StockQuantityNotFoundException("StockQuantity does not exist"));
    }

    

}