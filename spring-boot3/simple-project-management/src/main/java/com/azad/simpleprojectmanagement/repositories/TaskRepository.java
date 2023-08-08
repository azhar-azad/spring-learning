package com.azad.simpleprojectmanagement.repositories;

import com.azad.simpleprojectmanagement.models.task.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
