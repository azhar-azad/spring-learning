package com.azad.basicecommerce.productservice.models.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReviewRequest extends Review {

    private String productUid;
}
