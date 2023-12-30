package com.azad.supershop.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false, unique = true)
    private String phone;

    private String email;

    @Column(nullable = false)
    private String address;

    private double lifetimeBuyAmount;
    private Long totalTransactionCount;
    private double customerValue;
    private double totalDue;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TransactionEntity> transactions;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CartEntity> carts;
}
