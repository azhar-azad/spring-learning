package com.azad.ecommerce.productservice.models.dtos;

import com.azad.ecommerce.productservice.models.pojos.Rating;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RatingDto extends Rating {

    private Long id;
    private Long ratingUid;

    private Long productUid;
    private Long userUid;
}
