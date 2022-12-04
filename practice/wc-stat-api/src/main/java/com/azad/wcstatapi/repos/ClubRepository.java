package com.azad.wcstatapi.repos;

import com.azad.wcstatapi.models.entities.ClubEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends PagingAndSortingRepository<ClubEntity, Long> {
}
