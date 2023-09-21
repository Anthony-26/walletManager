package com.example.walletmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.walletmanager.entity.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long>{
    
}
