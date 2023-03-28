package com.azad.data.repos;

import com.azad.data.models.entities.TaskListEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskListRepository extends PagingAndSortingRepository<TaskListEntity, Long> {

    Optional<List<TaskListEntity>> findByUserId(Long userId, Pageable pageable);
}
