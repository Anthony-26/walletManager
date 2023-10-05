package com.example.walletmanager.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.walletmanager.entity.Stock;
import com.example.walletmanager.repository.StockRepository;
import com.example.walletmanager.service.StockService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final AlphaVantageServiceImpl alphaVantageServiceImpl;

    @Override
    public void saveStock(String ticker) {
        stockRepository.save(alphaVantageServiceImpl.findStockByTicker(ticker));
    }

    @Override
    public Stock findStockByTicker(String ticker) {
        Optional<Stock> optionalStock = stockRepository.findByTicker(null);
        if(optionalStock.isPresent()) return optionalStock.get();
        
        Stock stock = alphaVantageServiceImpl.findStockByTicker(ticker);
        stockRepository.save(stock);
        return stock;
    }

    @Override
    public Stock findStockById(Long id) {
        return stockRepository.findById(id).get();
    }

}