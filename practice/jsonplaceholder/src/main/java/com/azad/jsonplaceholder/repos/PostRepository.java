package com.azad.jsonplaceholder.repos;

import com.azad.jsonplaceholder.models.entities.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends PagingAndSortingRepository<PostEntity, Long> {

    long count();

    @Query(value = "select * from posts where user_id = :userId", nativeQuery = true)
    Optional<List<PostEntity>> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
