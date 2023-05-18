package com.azad.basicecommerce.productservice.models.product;

import com.azad.basicecommerce.productservice.models.category.CategoryEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_uid", nullable = false, unique = true)
    private String productUid;  // productName + brand + price

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

    @Column(name = "estimated_delivery_time", nullable = false)
    private String estimatedDeliveryTime;

    @Column(name = "available_in_stock", nullable = false)
    private Integer availableInStock;

    @Column(name = "low_stock_threshold", nullable = false)
    private Integer lowStockThreshold;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "return_policy")
    private String returnPolicy;

    @Column(name = "warranty")
    private String warranty;

    @Column(name = "average_rating", nullable = false)
    private Double averageRating;

    @Column(name = "total_review", nullable = false)
    private Long totalReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(name = "store_uid", nullable = false)
    private String storeUid;
}
