package com.azad.ecommerce.productservice.models.requests;

import com.azad.ecommerce.productservice.models.pojos.Rating;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RatingRequest extends Rating {

    private Long productUid;
}
