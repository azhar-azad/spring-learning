package com.azad.basicecommerce.productservice.models.review;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class Review {

    protected String reviewUid;

    @NotNull(message = "Review text cannot be empty")
    protected String reviewText;
}
