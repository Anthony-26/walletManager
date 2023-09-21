package com.example.walletmanager.service.impl;

import org.springframework.stereotype.Service;

import com.example.walletmanager.entity.Portfolio;
import com.example.walletmanager.repository.PortfolioRepository;
import com.example.walletmanager.service.PortfolioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Override
    public void save(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }

    @Override
    public Portfolio getPortfolioById(Long id) {
        return portfolioRepository.findById(id).get();
    }

    
}
