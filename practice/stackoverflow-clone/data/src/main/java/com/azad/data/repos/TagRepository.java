package com.azad.data.repos;

import com.azad.data.models.entities.TagEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends PagingAndSortingRepository<TagEntity, Long> {

    Optional<TagEntity> findByName(String name);
    Optional<List<TagEntity>> findByQuestionId(Long questionId);
}
