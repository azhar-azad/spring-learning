package com.azad.moviepedia.models.memberinfo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MemberInfo {

    @NotNull(message = "First name cannot be empty.")
    @Size(min = 1, max = 50, message = "First name length has to be between 1 to 50 characters.")
    protected String firstName;

    @NotNull(message = "Last name cannot be empty.")
    @Size(min = 1, max = 50, message = "Last name length has to be between 1 to 50 characters.")
    protected String lastName;

    @NotNull(message = "Occupation cannot be empty.")
    protected String occupation;

    protected Integer totalReviews;
    protected Integer totalRatings;
    protected Double avgRatingGiven;
}
