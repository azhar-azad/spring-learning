package com.azad.wcstatapi.repos;

import com.azad.wcstatapi.models.entities.PlayerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends PagingAndSortingRepository<PlayerEntity, Long> {

    Optional<List<PlayerEntity>> findByTeamId(Long teamId);
    Optional<PlayerEntity> findByTeamIdAndPlayerName(Long teamId, String playerName);
}
