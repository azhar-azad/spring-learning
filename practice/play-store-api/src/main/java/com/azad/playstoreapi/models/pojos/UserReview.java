package com.azad.playstoreapi.models.pojos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class UserReview {

    @NotNull(message = "Review text must be provided")
    @Size(min = 2, max = 500, message = "Review text must be between 2 to 500 characters.")
    protected String reviewText;
    protected LocalDate reviewDate;

    public UserReview() {
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}
