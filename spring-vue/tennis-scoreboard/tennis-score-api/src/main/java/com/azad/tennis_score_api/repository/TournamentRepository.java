package com.azad.tennis_score_api.repository;

import com.azad.tennis_score_api.model.entity.TournamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<TournamentEntity, Long> {
}
