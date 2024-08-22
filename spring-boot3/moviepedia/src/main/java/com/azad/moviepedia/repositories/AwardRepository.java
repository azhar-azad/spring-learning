package com.azad.moviepedia.repositories;

import com.azad.moviepedia.models.entities.AwardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AwardRepository extends JpaRepository<AwardEntity, Long> {
}
