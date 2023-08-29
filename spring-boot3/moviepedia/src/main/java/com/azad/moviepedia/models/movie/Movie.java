package com.azad.moviepedia.models.movie;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Movie {

    @NotNull(message = "Movie name cannot be null")
    protected String movieName;

    @NotNull(message = "Movie summary cannot be null")
    protected String summary;

    @NotNull(message = "Release year cannot be null")
    protected Integer releaseYear;

    @NotNull(message = "PG-Rating cannot be null")
    protected String pgRating;

    @NotNull(message = "Runtime cannot be null")
    protected String runtime;

//    protected String storyLine;

    protected Double memberRating;
    protected Double criticRating;
    protected Double imdbRating;
    protected Long totalRatings;

    protected Long totalMemberReviews;
    protected Long totalCriticReviews;

    protected Boolean isVerified;
}
