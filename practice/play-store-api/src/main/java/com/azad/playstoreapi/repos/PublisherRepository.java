package com.azad.playstoreapi.repos;

import com.azad.playstoreapi.models.entities.PublisherEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends PagingAndSortingRepository<PublisherEntity, Long> {

    long count();

    @Query(value = "select * from publisher where user_id = :userId", nativeQuery = true)
    Optional<List<PublisherEntity>> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(value = "select * from publisher where publisher_name = :publisherName", nativeQuery = true)
    Optional<PublisherEntity> findByPublisherName(@Param("publisherName") String publisherName);
}
