package com.azad.ecommerce.productservice.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "product_lines")
public class ProductLineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_line_uid", unique = true, nullable = false)
    private Long productLineUid; // id + productLineName

    @Column(name = "product_line_name", nullable = false)
    private String productLineName;
}
