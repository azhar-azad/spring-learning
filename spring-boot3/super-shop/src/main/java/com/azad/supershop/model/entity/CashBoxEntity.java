package com.azad.supershop.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "cash_boxes")
public class CashBoxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cash_box_id")
    private Integer id;

    @Column(name = "box_name", unique = true, nullable = false)
    private String boxName;

    @Column(nullable = false)
    private Double initialAmount;

    private Double currentAmount;
    private Long transactionCount;
}
