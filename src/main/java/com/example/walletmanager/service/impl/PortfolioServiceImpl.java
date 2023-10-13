package com.example.walletmanager.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.walletmanager.entity.Portfolio;
import com.example.walletmanager.exception.CustomExceptions.PortfolioNotFoundException;
import com.example.walletmanager.repository.PortfolioRepository;
import com.example.walletmanager.service.PortfolioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Override
    public void savePortfolio(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }

    @Override
    public Portfolio findPortfolioById(Long id) {
        if(id == null) throw new IllegalArgumentException("Portfolio cannot be null");
        return portfolioRepository.findById(id).orElseThrow(() -> new PortfolioNotFoundException("Portfolio does not exist"));
    }

    @Override
    public List<Portfolio> findAllPortfolios() {
        return portfolioRepository.findAll();
    }

    @Override
    public List<Portfolio> findPortfoliosByUserId(Long userId) {
        return portfolioRepository.findByUser_Id(userId);
    }

    @Override
    public boolean isStockQuantityExisting(Long portfolioId, String ticker) {
        if(portfolioId == null) throw new IllegalArgumentException("Portfolio cannot be null");
        if(ticker == null) throw new IllegalArgumentException("Ticker cannot be null");
        return portfolioRepository.countByPortfolioIdAndTicker(portfolioId, ticker) > 0L;
    }

}