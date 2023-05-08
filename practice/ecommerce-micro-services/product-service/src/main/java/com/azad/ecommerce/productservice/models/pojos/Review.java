package com.azad.ecommerce.productservice.models.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class Review {

    @NotNull(message = "Review text cannot be empty")
    protected String reviewText;
}
