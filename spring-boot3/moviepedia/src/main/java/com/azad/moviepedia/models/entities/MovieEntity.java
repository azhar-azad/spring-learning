package com.azad.moviepedia.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
@Data
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "release_year", nullable = false)
    private Integer year;

    @Column(name = "runtime", nullable = false)
    private Integer runtime;

    @Column(name = "plot")
    private String plot;

    @Column(name = "pg_rating")
    private String pgRating;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "total_votes")
    private Long totalVotes;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "movie_director",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id"))
    private Set<DirectorEntity> directors = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "movie_writer",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "writer_id"))
    private Set<WriterEntity> writers = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieCastEntity> movieCasts = new HashSet<>();

    @OneToMany(mappedBy = "movie")
    private Set<AwardEntity> awards = new HashSet<>();
}
