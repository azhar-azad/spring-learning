package com.azad.CampusConnectApi.repositories;

import com.azad.CampusConnectApi.models.entities.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<PostEntity, Long> {
    List<PostEntity> findByAppUserId(@Param("appUserId") Long appUserId, Pageable pageable);
}
