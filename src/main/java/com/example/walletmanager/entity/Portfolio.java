package com.example.walletmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "portfolios")
@Data
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user-portfolio")
    @NotNull
    private User user;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "portfolio-stockQuantity")
    private Set<StockQuantity> stocksQuantities = new HashSet<>();

    @Column
    private BigDecimal totalCurrentValue;

    public Portfolio() {
    }

    public Portfolio(User user) {
        this.user = user;
        this.totalCurrentValue = new BigDecimal(0);
    }

    public Portfolio(String name, User user) {
        this.name = name;
        this.user = user;
        this.totalCurrentValue = new BigDecimal(0);
    }

    public void addTotalCurrentValue(BigDecimal value) {
        this.totalCurrentValue = this.totalCurrentValue.add(value);
    }

    public void substractTotalCurrentValue(StockQuantity stockQuantity) {
        this.stocksQuantities.remove(stockQuantity);
        this.totalCurrentValue = this.totalCurrentValue.subtract(stockQuantity.getValue());
    }

    public void updateTotalCurrentValue(BigDecimal oldValue, BigDecimal newValue) {
        this.totalCurrentValue = this.totalCurrentValue.add(newValue.subtract(oldValue));
    }

    // public static boolean isStockPresent()

}