package com.azad.todolist.repos;

import com.azad.todolist.models.entities.TaskEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends PagingAndSortingRepository<TaskEntity, Long> {

    Optional<List<TaskEntity>> findByUserId(Pageable pageable, Long userId);

    Optional<TaskEntity> findByTaskId(String taskId);
}
