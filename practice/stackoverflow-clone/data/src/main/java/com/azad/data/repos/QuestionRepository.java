package com.azad.data.repos;

import com.azad.data.models.entities.QuestionEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<QuestionEntity, Long> {

    Optional<List<QuestionEntity>> findByUserId(Long userId);
}
