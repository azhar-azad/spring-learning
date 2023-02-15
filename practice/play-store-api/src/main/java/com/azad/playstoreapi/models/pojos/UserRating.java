package com.azad.playstoreapi.models.pojos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UserRating {

    @NotNull(message = "Rating value must be provided")
    @Min(1)
    @Max(5)
    protected Integer rating;

    public UserRating() {
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
