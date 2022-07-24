package com.azad.jsonplaceholderclone.repos;

import com.azad.jsonplaceholderclone.models.entities.TodoEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends PagingAndSortingRepository<TodoEntity, Long> {
}
