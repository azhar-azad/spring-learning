package com.azad.moviepedia.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "genres")
@Data
@NoArgsConstructor
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "movie_count", nullable = false)
    private Integer movieCount;

    @ManyToMany(mappedBy = "genres")
    private Set<MovieEntity> movies = new HashSet<>();

    public GenreEntity(String name) {
        this.name = name;
        this.movieCount = 0;
    }
}
