package com.azad.todolist.api;

import com.azad.todolist.common.ApiController;
import com.azad.todolist.common.AppUtils;
import com.azad.todolist.common.PagingAndSorting;
import com.azad.todolist.models.dtos.TodoItemDto;
import com.azad.todolist.models.dtos.TodoItemRequest;
import com.azad.todolist.models.dtos.TodoItemResponse;
import com.azad.todolist.services.TodoItemApiService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/todoItems")
public class TodoItemApiController implements ApiController<TodoItemRequest, TodoItemResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    private final TodoItemApiService service;

    @Autowired
    public TodoItemApiController(TodoItemApiService service) {
        this.service = service;
    }

    @PostMapping
    @Override
    public ResponseEntity<TodoItemResponse> createEntity(
            @RequestBody TodoItemRequest request) {

        TodoItemDto dto = modelMapper.map(request, TodoItemDto.class);

        TodoItemDto savedDto = service.create(dto);

        return new ResponseEntity<>(modelMapper.map(savedDto, TodoItemResponse.class),
                HttpStatus.CREATED);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<TodoItemResponse>> getAllEntity(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;

        List<TodoItemDto> dtos = service.getAll(new PagingAndSorting(
                page > 0 ? page - 1 : page, limit, sort, order));
        if (dtos.size() == 0)
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(dtos.stream()
                .map(dto -> modelMapper.map(dto, TodoItemResponse.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TodoItemResponse> getEntity(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<TodoItemResponse> updateEntity(Long id, TodoItemRequest updatedRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteEntity(Long id) {
        return null;
    }
}
