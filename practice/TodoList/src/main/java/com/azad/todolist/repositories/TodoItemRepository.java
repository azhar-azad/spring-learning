package com.azad.todolist.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.azad.todolist.entities.TodoItem;

@Repository
public interface TodoItemRepository extends PagingAndSortingRepository<TodoItem, Long> {
	
	TodoItem findByName(String name);

}
