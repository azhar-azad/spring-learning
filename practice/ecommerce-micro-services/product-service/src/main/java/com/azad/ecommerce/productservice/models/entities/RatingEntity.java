package com.azad.ecommerce.productservice.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "ratings")
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rating_uid", unique = true, nullable = false)
    private Long ratingUid; // id + ratingValue

    @Column(name = "rating_value", nullable = false)
    private Integer ratingValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "user_uid")
    private Long userUid;
}
