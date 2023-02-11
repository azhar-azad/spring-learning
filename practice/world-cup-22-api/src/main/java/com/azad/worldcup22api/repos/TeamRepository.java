package com.azad.worldcup22api.repos;

import com.azad.worldcup22api.models.entities.TeamEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends PagingAndSortingRepository<TeamEntity, Long> {
}
