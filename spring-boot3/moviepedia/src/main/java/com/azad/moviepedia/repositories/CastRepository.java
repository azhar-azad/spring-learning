package com.azad.moviepedia.repositories;

import com.azad.moviepedia.models.entities.CastEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CastRepository extends JpaRepository<CastEntity, Long> {
}
