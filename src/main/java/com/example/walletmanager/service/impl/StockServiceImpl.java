package com.example.walletmanager.service.impl;

import org.springframework.stereotype.Service;

import com.example.walletmanager.entity.Stock;
import com.example.walletmanager.repository.StockRepository;
import com.example.walletmanager.service.StockService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    @Override
    public void saveStock(Stock stock) {
        stockRepository.save(stock);
    }

    @Override
    public Stock getStockByTicker(String ticker) {
        return stockRepository.findByTicker(ticker);
    }

    @Override
    public Stock getStockById(Long id) {
        return stockRepository.findById(id).get();
    }
    
}