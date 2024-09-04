package com.azad.moviepedia.repositories;

import com.azad.moviepedia.models.entities.AwardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AwardRepository extends JpaRepository<AwardEntity, Long> {

    Optional<AwardEntity> findByNameAndYearAndCategory(String name, Integer year, String category);
}
