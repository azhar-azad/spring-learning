package com.azad.tennis_score_api.repository;

import com.azad.tennis_score_api.model.entity.SetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetRepository extends JpaRepository<SetEntity, Long> {
}
