package com.azad.moviepedia.models.pojos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Movie {

    @NotBlank(message = "Movie title cannot be empty")
    private String title;

    @NotNull(message = "Movie release year cannot be empty")
    private Integer year;

    @NotNull(message = "Movie runtime cannot be empty")
    private Integer runtime;

    private String plot;
    private String pgRating;
    private Double rating;
    private Long totalVotes;
}
