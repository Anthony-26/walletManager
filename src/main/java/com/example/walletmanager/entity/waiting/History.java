package com.example.walletmanager.entity.waiting;
// package com.example.walletmanager.entity;

// import jakarta.persistence.*;
// import lombok.Getter;
// import lombok.Setter;

// import java.time.LocalDate;

// @Entity
// @Table(name = "history")
// @Getter
// @Setter
// public class History {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "stock_id", nullable = false)
//     private Stock stock;

//     @Column(nullable = false)
//     private LocalDate date;

//     @Column(nullable = false)
//     private double closePrice;

//     @Column(nullable = false)
//     private double high;

//     @Column(nullable = false)
//     private double low;

//     public History(){
//     }

//     // autres méthodes utilitaires si nécessaire
// }