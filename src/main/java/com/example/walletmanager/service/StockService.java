package com.example.walletmanager.service;

import com.example.walletmanager.entity.Stock;

public interface StockService {

    public void saveStock(String ticker);

    public Stock findStockByTicker(String ticker);

    public Stock findStockById(Long id);

}
