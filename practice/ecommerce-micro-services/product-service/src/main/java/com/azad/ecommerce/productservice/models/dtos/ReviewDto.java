package com.azad.ecommerce.productservice.models.dtos;

import com.azad.ecommerce.productservice.models.pojos.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReviewDto extends Review {

    private Long id;
    private Long reviewUid;

    private Long productUid;
    private Long userUid;
}
