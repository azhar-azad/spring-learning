package com.azad.worldcup22api.repos;

import com.azad.worldcup22api.models.entities.PlayerStatEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerStatRepository extends PagingAndSortingRepository<PlayerStatEntity, Long> {
}
