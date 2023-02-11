package com.azad.worldcup22api.repos;

import com.azad.worldcup22api.models.entities.PlayerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends PagingAndSortingRepository<PlayerEntity, Long> {
}
