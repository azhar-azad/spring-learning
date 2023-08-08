package com.azad.simpleprojectmanagement.repositories;

import com.azad.simpleprojectmanagement.models.story.StoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<StoryEntity, Long> {
}
