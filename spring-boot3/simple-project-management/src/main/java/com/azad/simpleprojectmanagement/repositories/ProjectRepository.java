package com.azad.simpleprojectmanagement.repositories;

import com.azad.simpleprojectmanagement.models.project.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

//    Optional<List<ProjectEntity>> findByProjectManager(Long projectManagerUserId);
}
