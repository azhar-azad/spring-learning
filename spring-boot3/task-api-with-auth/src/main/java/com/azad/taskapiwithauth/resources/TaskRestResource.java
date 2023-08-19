package com.azad.taskapiwithauth.resources;

import com.azad.taskapiwithauth.commons.PagingAndSorting;
import com.azad.taskapiwithauth.commons.exceptions.ResourceNotFoundException;
import com.azad.taskapiwithauth.models.task.TaskDto;
import com.azad.taskapiwithauth.models.task.TaskRequest;
import com.azad.taskapiwithauth.models.task.TaskResponse;
import com.azad.taskapiwithauth.services.AuthService;
import com.azad.taskapiwithauth.services.TaskService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/tasks")
public class TaskRestResource {

    @Autowired
    private ModelMapper modelMapper;

    private final TaskService service;

    @Autowired
    public TaskRestResource(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {

        TaskDto dto = modelMapper.map(request, TaskDto.class);

        TaskDto savedDto = service.create(dto);

        return new ResponseEntity<>(modelMapper.map(savedDto, TaskResponse.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(
            @Valid @RequestParam(name = "page", defaultValue = "1") int page,
            @Valid @RequestParam(name = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(name = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(name = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;

        List<TaskDto> dtosFromService = service.getAllByUser(
                new PagingAndSorting(page > 1 ? page - 1 : page, limit, sort, order));
        if (dtosFromService == null || dtosFromService.isEmpty())
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(dtosFromService.stream()
                .map(dto -> modelMapper.map(dto, TaskResponse.class)).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TaskResponse> getTask(@Valid @PathVariable("id") Long id) {

        TaskDto dtoFromService;
        try {
            dtoFromService = service.getById(id);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        if (dtoFromService == null)
            return ResponseEntity.notFound().build();

        return new ResponseEntity<>(modelMapper.map(dtoFromService, TaskResponse.class), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @Valid @PathVariable("id") Long id, @RequestBody TaskRequest updatedRequest) {

        TaskDto dto = modelMapper.map(updatedRequest, TaskDto.class);

        TaskDto updatedDto;
        try {
            updatedDto= service.updateById(id, dto);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }


        return new ResponseEntity<>(modelMapper.map(updatedDto, TaskResponse.class), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteTask(@Valid @PathVariable("id") Long id) {

        try {
            service.deleteById(id);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Task Deleted");
    }
}
