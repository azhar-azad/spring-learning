package com.azad.moviepedia.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class MovieCastId implements Serializable {

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "cast_id")
    private Long castId;
}
