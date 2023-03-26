package com.azad.data.repos;

import com.azad.data.models.entities.SubtaskEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubtaskRepository extends PagingAndSortingRepository<SubtaskEntity, Long> {

    Optional<SubtaskEntity> findByTask(Long taskId);
}
