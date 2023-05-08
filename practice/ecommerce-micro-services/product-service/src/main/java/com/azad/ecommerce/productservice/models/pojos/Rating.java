package com.azad.ecommerce.productservice.models.pojos;

import com.azad.ecommerce.productservice.commons.validations.EnumValidator;
import com.azad.ecommerce.productservice.models.constants.RatingValues;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class Rating {

    @NotNull(message = "Rating value cannot be null")
    @EnumValidator(enumClass = RatingValues.class, message = "Rating value is not valid")
    protected Integer ratingValue;
}
