package com.azad.moviepedia.repositories;

import com.azad.moviepedia.models.entities.MovieCastEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCastRepository extends JpaRepository<MovieCastEntity, Long> {
}
