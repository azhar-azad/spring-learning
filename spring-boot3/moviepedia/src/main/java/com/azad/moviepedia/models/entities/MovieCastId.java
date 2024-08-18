package com.azad.moviepedia.models.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class MovieCastId implements Serializable {

    private Long movieId;
    private Long castId;
}
