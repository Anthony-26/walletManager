package com.example.walletmanager.service;

import com.example.walletmanager.entity.Stock;

public interface AlphaVantageService {
    
    Stock findStockByTicker(String ticker);

}
