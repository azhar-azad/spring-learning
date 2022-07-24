package com.azad.jsonplaceholderclone.controllers;

import com.azad.jsonplaceholderclone.models.Todo;
import com.azad.jsonplaceholderclone.models.dtos.TodoDto;
import com.azad.jsonplaceholderclone.models.responses.TodoResponse;
import com.azad.jsonplaceholderclone.services.TodoService;
import com.azad.jsonplaceholderclone.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/todos")
public class TodoController {

    @Autowired
    private ModelMapper modelMapper;

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodos(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page > 0) page--;

        List<TodoDto> allTodosFromService = todoService.getAll(new PagingAndSorting(page, limit, sort, order));
        if (allTodosFromService == null || allTodosFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<TodoResponse> todoResponses = allTodosFromService.stream()
                .map(todoDto -> modelMapper.map(todoDto, TodoResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(todoResponses, HttpStatus.OK);
    }

    @PostMapping(path = "/batch")
    public ResponseEntity<List<TodoResponse>> createMultipleTodos(@RequestBody List<Todo> todos) {

        List<TodoDto> todoDtos = todos.stream()
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());

        List<TodoDto> savedDtos = new ArrayList<>();
        for (TodoDto todoDto: todoDtos)
            savedDtos.add(todoService.create(todoDto));

        List<TodoResponse> todoResponses = savedDtos.stream()
                .map(todoDto -> modelMapper.map(todoDto, TodoResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(todoResponses, HttpStatus.OK);
    }
}
