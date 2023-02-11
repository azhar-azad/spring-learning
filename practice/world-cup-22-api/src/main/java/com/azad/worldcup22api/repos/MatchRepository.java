package com.azad.worldcup22api.repos;

import com.azad.worldcup22api.models.entities.MatchEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends PagingAndSortingRepository<MatchEntity, Long> {
}
