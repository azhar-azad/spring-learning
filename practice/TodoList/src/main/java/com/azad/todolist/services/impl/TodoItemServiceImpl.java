package com.azad.todolist.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.azad.todolist.entities.TodoItem;
import com.azad.todolist.exceptions.InvalidSortItemsException;
import com.azad.todolist.exceptions.RequestBodyEmptyException;
import com.azad.todolist.exceptions.ResourceNotFoundException;
import com.azad.todolist.repositories.TodoItemRepository;
import com.azad.todolist.services.TodoItemService;
import com.azad.todolist.utils.Utils;

@Service
public class TodoItemServiceImpl implements TodoItemService {

	private TodoItemRepository repository;

	@Autowired
	public TodoItemServiceImpl(TodoItemRepository repository) {
		this.repository = repository;
	}

	@Override
	public TodoItem createTodoItem(TodoItem todoItem) {
		
		if (todoItem == null) {
			throw new RequestBodyEmptyException("Request body is empty");
		}
		
		return repository.save(todoItem);
	}
	
	@Override
	public List<TodoItem> getAllTodoItems(int page, int limit) {
		
		Pageable pageable = PageRequest.of(page, limit);
		
		Page<TodoItem> todoItemsPage = repository.findAll(pageable);
		
		return todoItemsPage.getContent();
	}

	@Override
	public List<TodoItem> getAllTodoItems(int page, int limit, String sort, String order) {
		
		List<String> sortItems = Arrays.asList(sort.split(","));
		
//		Todo: This code throws ClassNotFoundException -- Find a workaround  
		boolean sortItemsAreValid = Utils.sortItemsAreValid("TodoItem", sortItems);
		
		if (!sortItemsAreValid) {
			throw new InvalidSortItemsException("Sort items are invalid for the resource TodoItem");
		}
		
		Sort sortBy = Sort.by(sortItems.get(0));
		for (int i = 1; i < sortItems.size(); i++) {
			sortBy = sortBy.and(Sort.by(sortItems.get(i)));
		}
		
		if (order.equalsIgnoreCase("desc")) {
			sortBy = sortBy.descending();
		}
		
		Pageable pageable = PageRequest.of(page, limit, sortBy);
		Page<TodoItem> todoItemsPage = repository.findAll(pageable);
		
		return todoItemsPage.getContent();
	}

	@Override
	public TodoItem getTodoItemById(Long id) {
		
		TodoItem todoItem = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TodoItem", id));
		
		return todoItem;
	}

	@Override
	public TodoItem getTodoItemByName(String name) {
		
		TodoItem todoItem = repository.findByName(name);
		
		if (todoItem == null) {
			throw new ResourceNotFoundException("TodoItem", name);
		}
		
		return todoItem;
	}

	@Override
	public TodoItem updateTodoItem(Long id, TodoItem todoItem) {
		
		TodoItem existingTodoItem = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TodoItem", id));
		
		existingTodoItem.setName(todoItem.getName());
		existingTodoItem.setDoBefore(todoItem.getDoBefore());
		
		return existingTodoItem;
	}

	@Override
	public void deleteTodoItem(Long id) {
		
		TodoItem todoItem = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TodoItem", id));
		
		repository.delete(todoItem);
	}
	
	
}
