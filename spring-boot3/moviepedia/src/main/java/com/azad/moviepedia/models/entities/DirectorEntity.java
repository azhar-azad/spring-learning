package com.azad.moviepedia.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "directors")
@Data
public class DirectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_id")
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

    @OneToMany(mappedBy = "director")
    private Set<AwardEntity> awards = new HashSet<>();
}
