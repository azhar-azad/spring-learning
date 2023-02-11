package com.azad.worldcup22api.repos;

import com.azad.worldcup22api.models.entities.TeamStatEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamStatRepository extends PagingAndSortingRepository<TeamStatEntity, Long> {
}
