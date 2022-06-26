package com.azad.todolist.repos;

import com.azad.todolist.models.entities.TaskEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends PagingAndSortingRepository<TaskEntity, Long> {
}
