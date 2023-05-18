package com.azad.basicecommerce.productservice.models.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReviewDto extends Review {

    private Long id;
    private String productUid;
    private String reviewerUid;
}
