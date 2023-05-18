package com.azad.basicecommerce.productservice.models.review;

import com.azad.basicecommerce.auth.models.AppUserEntity;
import com.azad.basicecommerce.productservice.models.product.ProductEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "reviews")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(name = "review_uid", nullable = false, unique = true)
    private String reviewUid;   // reviewText

    @Column(name = "review_text", nullable = false)
    private String reviewText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "reviewer_uid", nullable = false)
    private String reviewerUid;
}
