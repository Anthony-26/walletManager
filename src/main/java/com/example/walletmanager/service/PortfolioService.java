package com.example.walletmanager.service;

import java.util.List;

import com.example.walletmanager.entity.Portfolio;

public interface PortfolioService {
    
    public void save(Portfolio portfolio);

    public Portfolio getPortfolioById(Long id);

    public List<Portfolio> findAllPortfolios();

}
