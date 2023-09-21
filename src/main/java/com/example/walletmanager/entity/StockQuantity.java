package com.example.walletmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stock_quantities")
@Getter
@Setter
public class StockQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @Column
    @NotNull
    @PositiveOrZero
    private int quantity;

    @Column
    private int value;

    public StockQuantity() {
    }

    public StockQuantity(Stock stock, Portfolio portfolio, int quantity) {
        this.stock = stock;
        this.portfolio = portfolio;
        this.quantity = quantity;
    }

    public double getValue(){
        return this.stock.getCurrentPrice() * this.quantity;
    }
}