package com.azad.jsonplaceholder.repos;

import com.azad.jsonplaceholder.models.entities.CommentEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<CommentEntity, Long> {

    long count();
}
