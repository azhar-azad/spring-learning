package com.azad.data.repos;

import com.azad.data.models.entities.TaskEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<TaskEntity, Long> {

    Optional<List<TaskEntity>> findByTaskList(Long listId, Pageable pageable);
}
