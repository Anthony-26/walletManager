package com.example.walletmanager.service;

import java.util.List;

import com.example.walletmanager.entity.Portfolio;

public interface PortfolioService {

    public void savePortfolio(Portfolio portfolio);

    public Portfolio findPortfolioById(Long id);

    public List<Portfolio> findPortfoliosByUserId(Long userId);

    public List<Portfolio> findAllPortfolios();

    public boolean isStockQuantityExisting(Long portfolioId, String ticker);

}
