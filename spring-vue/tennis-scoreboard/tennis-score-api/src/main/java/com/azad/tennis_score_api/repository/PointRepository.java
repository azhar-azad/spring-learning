package com.azad.tennis_score_api.repository;

import com.azad.tennis_score_api.model.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<PointEntity, Long> {
}
