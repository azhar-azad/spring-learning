package com.azad.taskapiwithauth.repositories;

import com.azad.taskapiwithauth.models.task.TaskEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Optional<List<TaskEntity>> findByUserId(Long userId, Pageable pageable);
}
