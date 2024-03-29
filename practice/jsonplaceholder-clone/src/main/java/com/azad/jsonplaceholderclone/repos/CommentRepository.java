package com.azad.jsonplaceholderclone.repos;

import com.azad.jsonplaceholderclone.models.entities.CommentEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<CommentEntity, Long> {
}
