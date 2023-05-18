package com.azad.basicecommerce.productservice.models.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReviewResponse extends Review {

    private Long id;
    private String productUid;
    private String reviewerUid;
}
