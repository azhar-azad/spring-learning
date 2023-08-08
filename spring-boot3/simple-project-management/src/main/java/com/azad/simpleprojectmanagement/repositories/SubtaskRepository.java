package com.azad.simpleprojectmanagement.repositories;

import com.azad.simpleprojectmanagement.models.subtask.SubtaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtaskRepository extends JpaRepository<SubtaskEntity, Long> {
}
