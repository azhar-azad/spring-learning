package com.azad.jsonplaceholder.repos;

import com.azad.jsonplaceholder.models.entities.TodoEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends PagingAndSortingRepository<TodoEntity, Long> {

    long count();

    @Query(value = "select * from todos where user_id = :userId", nativeQuery = true)
    Optional<List<TodoEntity>> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
