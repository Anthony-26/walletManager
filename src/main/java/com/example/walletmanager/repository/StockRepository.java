package com.example.walletmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.walletmanager.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByTicker(String string);
    
}
