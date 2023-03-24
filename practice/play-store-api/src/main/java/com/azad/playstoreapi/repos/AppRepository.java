package com.azad.playstoreapi.repos;

import com.azad.playstoreapi.models.entities.AppEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppRepository extends PagingAndSortingRepository<AppEntity, Long> {

    long count();

    @Query(value = "select * from app where publisher_id = :publisherId", nativeQuery = true)
    Optional<List<AppEntity>> findByPublisherId(@Param("publisherId") Long publisherId, Pageable pageable);
}
