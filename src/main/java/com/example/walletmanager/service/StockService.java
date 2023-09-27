package com.example.walletmanager.service;

import com.example.walletmanager.entity.Stock;

public interface StockService {

    public void saveStock(Stock stock);

    public Stock getStockByTicker(String ticker);

    public Stock getStockById(Long id);
    
}
