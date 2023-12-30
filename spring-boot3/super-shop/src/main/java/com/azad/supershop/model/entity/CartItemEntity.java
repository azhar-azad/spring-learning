package com.azad.supershop.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "cart_items")
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String unit;

    private double subtotalPrice;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductEntity product;
}
