package com.azad.data.repos;

import com.azad.data.models.entities.AccessEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessRepository extends PagingAndSortingRepository<AccessEntity, Long> {

    Optional<List<AccessEntity>> findByAppUserId(Long appUserId);
    Optional<List<AccessEntity>> findByTaskListId(Long taskListId);
}
