package com.azad.ecommerce.productservice.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_uid", unique = true, nullable = false)
    private Long productUid; // id + productName + brand

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "picture_url", nullable = false)
    private String pictureUrl;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "estimated_delivery_time", nullable = false)
    private String estimated_DeliveryTime;

    @Column(name = "return_policy")
    private String returnPolicy;

    @Column(name = "warranty")
    private String warranty;

    @Column(name = "available_in_stock", nullable = false)
    private Integer availableInStock;

    @Column(name = "low_stock_threshold", nullable = false)
    private Integer lowStockThreshold;

    @Column(name = "average_rating", nullable = false)
    private Double averageRating;

    @Column(name = "total_review", nullable = false)
    private Long totalReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReviewEntity> reviews;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RatingEntity> ratings;

    @Column(name = "store_uid")
    private Long storeUid;
}
