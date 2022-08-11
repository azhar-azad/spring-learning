package com.azad.jsonplaceholder.rest.controllers;

import com.azad.jsonplaceholder.models.Todo;
import com.azad.jsonplaceholder.models.dtos.TodoDto;
import com.azad.jsonplaceholder.models.responses.TodoResponse;
import com.azad.jsonplaceholder.rest.assemblers.TodoModelAssembler;
import com.azad.jsonplaceholder.security.auth.api.AuthService;
import com.azad.jsonplaceholder.services.TodoService;
import com.azad.jsonplaceholder.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/todos")
public class TodoRestController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthService authService;

    private final TodoService todoService;
    private final TodoModelAssembler todoModelAssembler;

    @Autowired
    public TodoRestController(TodoService todoService, TodoModelAssembler todoModelAssembler) {
        this.todoService = todoService;
        this.todoModelAssembler = todoModelAssembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<TodoResponse>> createTodo(@Valid @RequestBody Todo todo) {

        TodoDto todoDto = modelMapper.map(todo, TodoDto.class);

        TodoDto savedTodoDto = todoService.create(todoDto);

        TodoResponse todoResponse = modelMapper.map(savedTodoDto, TodoResponse.class);
        EntityModel<TodoResponse> todoResponseEntityModel = todoModelAssembler.toModel(todoResponse);

        return new ResponseEntity<>(todoResponseEntityModel, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<TodoResponse>>> getAllTodos(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;

        List<TodoDto> allTodosFromService = todoService.getAll(
                new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (allTodosFromService == null || allTodosFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<TodoResponse> todoResponses = allTodosFromService.stream()
                .map(todoDto -> modelMapper.map(todoDto, TodoResponse.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<TodoResponse>> collectionModel =
                todoModelAssembler.getCollectionModel(todoResponses, new PagingAndSorting(page, limit, sort, order));

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EntityModel<TodoResponse>> getTodo(@Valid @PathVariable("id") Long id) {

        TodoDto todoFromService = todoService.getById(id);
        if (todoFromService == null)
            return ResponseEntity.notFound().build();

        TodoResponse todoResponse = modelMapper.map(todoFromService, TodoResponse.class);
        EntityModel<TodoResponse> todoResponseEntityModel = todoModelAssembler.toModel(todoResponse);

        return new ResponseEntity<>(todoResponseEntityModel, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EntityModel<TodoResponse>> updateTodo(@Valid @PathVariable("id") Long id, @RequestBody Todo updatedRequestBody) {

        TodoDto todoFromService = todoService.updateById(id, modelMapper.map(updatedRequestBody, TodoDto.class));

        TodoResponse todoResponse = modelMapper.map(todoFromService, TodoResponse.class);
        EntityModel<TodoResponse> todoResponseEntityModel = todoModelAssembler.toModel(todoResponse);

        return new ResponseEntity<>(todoResponseEntityModel, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteTodo(@Valid @PathVariable("id") Long id) {

        todoService.deleteById(id);

        return ResponseEntity.notFound().build();
    }
}
