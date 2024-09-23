package com.azad.tennis_score_api.repository;

import com.azad.tennis_score_api.model.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity, Long> {
}
