package com.example.walletmanager.entity;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
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

    @Column(unique = true)
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

    @OneToMany(mappedBy = "stock", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "stock-stockQuantity")
    private Set<StockQuantity> stockQuantities;

    public Stock() {
    }

    public Stock(String ticker, Set<StockQuantity> stockQuantities) {
        this.ticker = ticker;
        this.stockQuantities = stockQuantities;
    }
}