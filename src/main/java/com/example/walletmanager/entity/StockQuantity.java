package com.example.walletmanager.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    @JsonIgnore
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    @JsonBackReference(value = "portfolio-stockQuantity")
    private Portfolio portfolio;

    @Column
    private String ticker;

    @Column
    @NotNull
    @PositiveOrZero
    private int quantity;

    @Column
    private BigDecimal value;

    public StockQuantity() {
    }

    public StockQuantity(Stock stock, Portfolio portfolio, int quantity) {
        this.stock = stock;
        this.portfolio = portfolio;
        this.quantity = quantity;
        this.ticker = stock.getTicker();
    }

    public BigDecimal getValue(){
        return this.stock.getCurrentPrice().multiply(BigDecimal.valueOf(this.quantity));
    }

    public String getTicker(){
        return this.stock.getTicker();
    }
    
}