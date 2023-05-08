package com.azad.ecommerce.productservice.models.responses;

import com.azad.ecommerce.productservice.models.pojos.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReviewResponse extends Review {

    private Long id;
    private Long reviewUid;

    private Long productUid;
    private Long userUid;
}
