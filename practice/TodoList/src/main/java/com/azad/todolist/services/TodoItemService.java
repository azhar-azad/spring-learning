package com.azad.todolist.services;

import java.util.List;

import com.azad.todolist.entities.TodoItem;

public interface TodoItemService {

	TodoItem createTodoItem(TodoItem todoItem);
	
	List<TodoItem> getAllTodoItems(int page, int limit);

	List<TodoItem> getAllTodoItems(int page, int limit, String sort, String order);
	
	TodoItem getTodoItemById(Long id);
	
	TodoItem getTodoItemByName(String name);

	TodoItem updateTodoItem(Long id, TodoItem todoItem);
	
	void deleteTodoItem(Long id);

}
