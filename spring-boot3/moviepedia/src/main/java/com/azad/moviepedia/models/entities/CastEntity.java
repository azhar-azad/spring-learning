package com.azad.moviepedia.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "casts")
@Data
public class CastEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cast_id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "birth_place")
    private String birthPlace;

    @Column(name = "mini_bio", length = 500)
    @Lob
    private String miniBio;

    @Column(name = "age", nullable = false)
    private String age;

    @OneToMany(mappedBy = "cast", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieCastEntity> movieCasts = new HashSet<>();

    @OneToMany(mappedBy = "cast")
    private Set<AwardEntity> awards = new HashSet<>();
}
