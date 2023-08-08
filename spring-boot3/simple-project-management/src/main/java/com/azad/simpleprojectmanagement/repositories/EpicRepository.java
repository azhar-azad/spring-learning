package com.azad.simpleprojectmanagement.repositories;

import com.azad.simpleprojectmanagement.models.epic.EpicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpicRepository extends JpaRepository<EpicEntity, Long> {
}
