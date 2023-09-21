package com.example.walletmanager.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
// import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stocks")
@Getter
@Setter
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String ticker;

    @Column
    private double currentPrice;

    @Column
    private double openPrice;

    @Column
    private double previousSessionHigh;

    @Column
    private double previousSessionLow;

    @Column
    private double previousSessionPercentageChange;

    @Column
    private double previousSessionChange;

    @Column
    private LocalDate latestTradingDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    public Stock() {
    }

    public Stock(String ticker, Portfolio portfolio) {
        this.ticker = ticker;
        this.portfolio = portfolio;
    }
}