package com.example.walletmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.walletmanager.entity.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long>{

    List<Portfolio> findByUser_Id(Long userId);
    
}
