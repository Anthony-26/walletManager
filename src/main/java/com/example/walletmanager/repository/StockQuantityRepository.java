package com.example.walletmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.walletmanager.entity.StockQuantity;

public interface StockQuantityRepository extends JpaRepository<StockQuantity, Long>{
    
}
