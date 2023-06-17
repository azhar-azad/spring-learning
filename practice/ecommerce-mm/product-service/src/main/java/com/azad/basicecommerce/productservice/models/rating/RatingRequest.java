package com.azad.basicecommerce.productservice.models.rating;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RatingRequest extends Rating {

    private String productUid;
}
