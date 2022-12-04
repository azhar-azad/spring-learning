package com.azad.wcstatapi.repos;

import com.azad.wcstatapi.models.entities.TeamDataEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamDataRepository extends PagingAndSortingRepository<TeamDataEntity, Long> {

    Optional<TeamDataEntity> findByTeamId(Long teamId);
}
