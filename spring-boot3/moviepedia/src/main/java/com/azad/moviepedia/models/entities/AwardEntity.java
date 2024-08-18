package com.azad.moviepedia.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "awards")
@Data
public class AwardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "award_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "winner")
    private boolean winner;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(name = "cast_id")
    private CastEntity cast;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private DirectorEntity director;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private WriterEntity writer;
}
