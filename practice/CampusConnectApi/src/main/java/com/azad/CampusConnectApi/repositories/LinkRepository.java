package com.azad.CampusConnectApi.repositories;

import com.azad.CampusConnectApi.models.entities.LinkEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface LinkRepository extends PagingAndSortingRepository<LinkEntity, Long> {

    @Transactional
    void deleteByPostId(Long postId);

    @Transactional
    @Modifying
    @Query(value = "delete from link where post_id is null", nativeQuery = true)
    void deleteLinksWithoutPostIdMapping();
}
