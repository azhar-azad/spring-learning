package com.azad.moviepedia.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "movie_cast")
@Data
public class MovieCastEntity {

    @EmbeddedId
    private MovieCastId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("castId")
    @JoinColumn(name = "cast_id")
    private CastEntity cast;

    @Column(name = "character_name")
    private String characterName;
}
