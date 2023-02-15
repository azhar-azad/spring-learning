package com.azad.playstoreapi.repos;

import com.azad.playstoreapi.models.entities.RatingHistoryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingHistoryRepository extends PagingAndSortingRepository<RatingHistoryEntity, Long> {
}
