package com.azad.ecommerce.productservice.models.requests;

import com.azad.ecommerce.productservice.models.pojos.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReviewRequest extends Review {

    private Long productUid;
}
