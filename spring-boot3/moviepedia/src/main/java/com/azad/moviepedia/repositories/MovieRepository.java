package com.azad.moviepedia.repositories;

import com.azad.moviepedia.models.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    Optional<List<MovieEntity>> findByTitle(String title);
    Optional<List<MovieEntity>> findByYear(int year);
}
