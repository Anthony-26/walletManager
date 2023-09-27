package com.example.walletmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
    private double totalCurrentValue;

    public Portfolio() {
    }

    public Portfolio(User user) {
        this.user = user;
    }

    public Portfolio(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void addStockQuantity(StockQuantity stockQuantity) {
        this.stocksQuantities.add(stockQuantity);
        this.totalCurrentValue += stockQuantity.getValue();
    }
    
    public void removeStockQuantity(StockQuantity stockQuantity) {
        this.stocksQuantities.remove(stockQuantity);
        this.totalCurrentValue -= stockQuantity.getValue();
    }
    
    public void updateStockQuantity(StockQuantity stockQuantity, int newQuantity) {
        double oldValue = stockQuantity.getValue();
        stockQuantity.setQuantity(newQuantity);
        double newValue = stockQuantity.getValue();
        this.totalCurrentValue += (newValue - oldValue);
    }

    // public static boolean isStockPresent()

}