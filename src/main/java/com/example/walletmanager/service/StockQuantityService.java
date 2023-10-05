package com.example.walletmanager.service;

import com.example.walletmanager.entity.StockQuantity;

public interface StockQuantityService {

    public void saveStockQuantity(StockQuantity stockQuantity);

    public StockQuantity getStockQuantityById(Long id);
    
}
