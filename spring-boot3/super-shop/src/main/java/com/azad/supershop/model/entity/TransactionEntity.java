package com.azad.supershop.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @Column(name = "transaction_date")
    private LocalDate date;

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    private double paidAmount;

    private double dueAmount;

    @Column(nullable = false)
    private String paymentMethod;

    private String receiverName;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
}
