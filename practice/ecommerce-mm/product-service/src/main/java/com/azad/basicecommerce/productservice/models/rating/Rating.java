package com.azad.basicecommerce.productservice.models.rating;

import com.azad.basicecommerce.common.validators.EnumValidator;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class Rating {

    protected String ratingUid;

    @NotNull(message = "Rating value cannot be null")
    @EnumValidator(enumClass = RatingValues.class, message = "Rating value is not valid")
    protected Integer ratingValue;
}