package com.azad.todolist.repositorites;

import com.azad.todolist.models.entities.TodoItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItemEntity, Long> {
}
