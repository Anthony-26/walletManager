package com.example.walletmanager.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stocks")
@Getter
@Setter
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String ticker;

    @Column
    private BigDecimal currentPrice;

    @Column
    private BigDecimal openPrice;

    @Column
    private BigDecimal previousSessionHigh;

    @Column
    private BigDecimal previousSessionLow;

    @Column
    private BigDecimal previousSessionPercentageChange;

    @Column
    private BigDecimal previousSessionChange;

    @Column
    private LocalDate latestTradingDay;

    @OneToMany(mappedBy = "stock", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<StockQuantity> stockQuantities;

    public Stock() {
    }

    public Stock(String ticker, Set<StockQuantity> stockQuantities) {
        this.ticker = ticker;
        this.stockQuantities = stockQuantities;
    }

    public Stock(String ticker, BigDecimal currentPrice, BigDecimal openPrice, BigDecimal previousSessionHigh, BigDecimal previousSessionLow, BigDecimal previousSessionPercentageChange, BigDecimal previousSessionChange, LocalDate latestTradingDay) {
        this.ticker = ticker;
        this.currentPrice = currentPrice;
        this.openPrice = openPrice;
        this.previousSessionHigh = previousSessionHigh;
        this.previousSessionLow = previousSessionLow;
        this.previousSessionPercentageChange = previousSessionPercentageChange;
        this.previousSessionChange = previousSessionChange;
        this.latestTradingDay = latestTradingDay;
    }

}