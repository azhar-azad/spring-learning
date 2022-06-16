package com.azad.CampusConnectApi.repositories;

import com.azad.CampusConnectApi.models.entities.CommentEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<CommentEntity, Long> {
}
