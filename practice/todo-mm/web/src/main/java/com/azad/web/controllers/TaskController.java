package com.azad.web.controllers;

import com.azad.common.PagingAndSorting;
import com.azad.data.models.dtos.TaskDto;
import com.azad.data.models.dtos.TaskListDto;
import com.azad.data.models.requests.TaskRequest;
import com.azad.data.models.responses.TaskListResponse;
import com.azad.data.models.responses.TaskResponse;
import com.azad.service.task.TaskService;
import com.azad.web.assemblers.TaskModelAssembler;
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
@RequestMapping(path = "/api/v1/tasks")
public class TaskController implements GenericApiRestController<TaskRequest, TaskResponse> {

    @Autowired
    private ModelMapper modelMapper;

    private final TaskService service;
    private final TaskModelAssembler assembler;

    @Autowired
    public TaskController(TaskService service, TaskModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<TaskResponse>> createEntity(@Valid @RequestBody TaskRequest request) {
        TaskDto dto = modelMapper.map(request, TaskDto.class);
        TaskDto taskFromService = service.create(dto);

        TaskResponse response = modelMapper.map(taskFromService, TaskResponse.class);
        EntityModel<TaskResponse> responseEntityModel = assembler.toModel(response);

        return new ResponseEntity<>(responseEntityModel, HttpStatus.CREATED);
    }

    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<EntityModel<TaskResponse>>> getAllEntities(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;

        List<TaskDto> allTasksFromService = service.getAll(
                new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (allTasksFromService.size() == 0)
            return ResponseEntity.noContent().build();

        return getCollectionModelResponseEntity(page, limit, sort, order, allTasksFromService);
    }

    @GetMapping(path = "/byList/{taskListId}")
    public ResponseEntity<CollectionModel<EntityModel<TaskResponse>>> getAllEntitiesByTaskList(
            @Valid @PathVariable("taskListId") Long taskListId,
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;

        List<TaskDto> allTasksFromService = service.getAllByList(
                taskListId, new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (allTasksFromService.size() == 0)
            return ResponseEntity.noContent().build();

        return getCollectionModelResponseEntity(page, limit, sort, order, allTasksFromService);
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<TaskResponse>> getEntity(@Valid @PathVariable("id") Long id) {

        TaskDto taskFromService = service.getById(id);
        if (taskFromService == null)
            return ResponseEntity.notFound().build();

        TaskResponse response = modelMapper.map(taskFromService, TaskResponse.class);
        EntityModel<TaskResponse> responseEntityModel = assembler.toModel(response);

        return new ResponseEntity<>(responseEntityModel, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<TaskResponse>> updateEntity(@Valid @PathVariable("id") Long id, @RequestBody TaskRequest updatedRequest) {

        TaskDto taskFromService = service.updateById(id, modelMapper.map(updatedRequest, TaskDto.class));

        TaskResponse taskResponse = modelMapper.map(taskFromService, TaskResponse.class);
        EntityModel<TaskResponse> responseEntityModel = assembler.toModel(taskResponse);

        return new ResponseEntity<>(responseEntityModel, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("id") Long id) {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<CollectionModel<EntityModel<TaskResponse>>> getCollectionModelResponseEntity(
            int page, int limit, String sort, String order, List<TaskDto> allTasksFromService) {

        List<TaskResponse> taskResponses = allTasksFromService.stream()
                .map(taskDto -> modelMapper.map(taskDto, TaskResponse.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<TaskResponse>> responseCollectionModel =
                assembler.getCollectionModel(taskResponses, new PagingAndSorting(page, limit, sort, order));

        return new ResponseEntity<>(responseCollectionModel, HttpStatus.OK);
    }
}
