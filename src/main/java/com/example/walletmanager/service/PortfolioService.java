package com.example.walletmanager.service;

import com.example.walletmanager.entity.Portfolio;

public interface PortfolioService {
    
    public void save(Portfolio portfolio);

    public Portfolio getPortfolioById(Long id);

}
