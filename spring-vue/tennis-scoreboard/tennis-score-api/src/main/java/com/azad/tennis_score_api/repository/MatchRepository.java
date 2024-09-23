package com.azad.tennis_score_api.repository;

import com.azad.tennis_score_api.model.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchEntity, Long> {
}
