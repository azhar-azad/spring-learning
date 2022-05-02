package com.azad.todolist.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azad.todolist.entities.TodoItem;
import com.azad.todolist.exceptions.RequestBodyEmptyException;
import com.azad.todolist.requests.TodoItemRequest;
import com.azad.todolist.responses.TodoItemResponse;
import com.azad.todolist.services.TodoItemService;
import com.azad.todolist.utils.Utils;

@RestController
@RequestMapping(path = "/api/v1/todoItems")
public class TodoItemController {

	private ModelMapper modelMapper = new ModelMapper();

	private TodoItemService service;

	@Autowired
	public TodoItemController(TodoItemService service) {
		this.service = service;
	}

	@GetMapping(path = "/heartbeat")
	public ResponseEntity<String> todoItemsRouteTest() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<TodoItemResponse> createTodoItem(@Valid @RequestBody TodoItemRequest todoItemRequest) {

		Utils.printControllerMethodInfo("POST", "/api/v1/todoItem", "createTodoItem");

		if (todoItemRequest == null) {
			throw new RequestBodyEmptyException("Empty body is posted to create a new Todo");
		}

		TodoItem createdTodoItem = service.createTodoItem(modelMapper.map(todoItemRequest, TodoItem.class));

		if (createdTodoItem == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<TodoItemResponse>(modelMapper.map(createdTodoItem, TodoItemResponse.class),
				HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<TodoItemResponse>> getAllTodoItems(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit,
			@RequestParam(value = "sort", defaultValue = "") String sort,
			@RequestParam(value = "order", defaultValue = "asc") String order) {

		Utils.printControllerMethodInfo("GET", "/api/v1/todoItem", "getAllTodoItems");

		if (page > 0) {
			page--;
		}

		List<TodoItem> todoItems = null;

		if (sort.isEmpty()) {
			todoItems = service.getAllTodoItems(page, limit);
		} else {
			todoItems = service.getAllTodoItems(page, limit, sort, order);
		}

		if (todoItems == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		List<TodoItemResponse> todoItemResponses = new ArrayList<>();
		todoItems.forEach(todoItem -> todoItemResponses.add(modelMapper.map(todoItem, TodoItemResponse.class)));

		return new ResponseEntity<List<TodoItemResponse>>(todoItemResponses, HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<TodoItemResponse> getTodoItemById(@Valid @PathVariable Long id) {

		Utils.printControllerMethodInfo("GET", "/api/v1/todoItem/{id}", "getTodoItemById");

		TodoItem todoItem = service.getTodoItemById(id);

		if (todoItem == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<TodoItemResponse>(modelMapper.map(todoItem, TodoItemResponse.class), HttpStatus.OK);
	}

	@GetMapping(path = "/byName/{name}")
	public ResponseEntity<TodoItemResponse> getTodoItemByName(@Valid @PathVariable String name) {

		Utils.printControllerMethodInfo("GET", "/api/v1/todoItems/byName/{name}", "getTodoItemByName");

		TodoItem todoItem = service.getTodoItemByName(name);

		if (todoItem == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<TodoItemResponse>(modelMapper.map(todoItem, TodoItemResponse.class), HttpStatus.OK);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<TodoItemResponse> updateTodoItem(@Valid @PathVariable Long id,
			@RequestBody TodoItemRequest todoItemRequest) {

		Utils.printControllerMethodInfo("PUT", "/api/v1/todoItems/{id}", "updateTodoItem");

		if (todoItemRequest == null) {
			throw new RequestBodyEmptyException("Empty body is posted to update an existing Todo");
		}

		TodoItem todoItem = service.updateTodoItem(id, modelMapper.map(todoItemRequest, TodoItem.class));

		if (todoItem == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<TodoItemResponse>(modelMapper.map(todoItem, TodoItemResponse.class), HttpStatus.OK);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<TodoItemResponse> deleteTodoItem(@Valid @PathVariable Long id) {

		Utils.printControllerMethodInfo("DELETE", "/api/v1/todoItems/{name}", "deleteTodoItem");

		service.deleteTodoItem(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
