package com.example.walletmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.walletmanager.entity.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long>{

    List<Portfolio> findByUser_Id(Long userId);

    @Query("SELECT COUNT(sq) FROM StockQuantity sq WHERE sq.portfolio.id = :portfolioId AND sq.ticker = :ticker")
    long countByPortfolioIdAndTicker(@Param("portfolioId") Long portfolioId, @Param("ticker") String ticker);
    
}
