package com.azad.moviepedia.repositories;

import com.azad.moviepedia.models.constants.GenreName;
import com.azad.moviepedia.models.entities.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {

    Optional<GenreEntity> findByName(String name);
}
