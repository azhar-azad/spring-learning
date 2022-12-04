package com.azad.wcstatapi.repos;

import com.azad.wcstatapi.models.entities.TeamEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends PagingAndSortingRepository<TeamEntity, Long> {

    Optional<TeamEntity> findByTeamName(String teamName);
}
