package com.azad.web.controllers;

import com.azad.common.PagingAndSorting;
import com.azad.data.models.dtos.TaskListDto;
import com.azad.data.models.pojos.TaskList;
import com.azad.data.models.requests.TaskListRequest;
import com.azad.data.models.responses.TaskListResponse;
import com.azad.service.tasklist.TaskListService;
import com.azad.web.assemblers.TaskListModelAssembler;
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
@RequestMapping(path = "/api/v1/tasklists")
public class TaskListController implements GenericApiRestController<TaskListRequest, TaskListResponse> {

    @Autowired
    private ModelMapper modelMapper;

    private final TaskListService service;
    private final TaskListModelAssembler assembler;

    @Autowired
    public TaskListController(TaskListService service, TaskListModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    @Override
    public ResponseEntity<EntityModel<TaskListResponse>> createEntity(@Valid @RequestBody TaskListRequest request) {

        TaskListDto dto = modelMapper.map(request, TaskListDto.class);
        TaskListDto taskListFromService = service.create(dto);

        TaskListResponse response = modelMapper.map(taskListFromService, TaskListResponse.class);
        EntityModel<TaskListResponse> responseEntityModel = assembler.toModel(response);

        return new ResponseEntity<>(responseEntityModel, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<TaskListResponse>>> getAllEntities(
            @Valid @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
            @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
            @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (page < 0) page = 0;

        List<TaskListDto> allTaskListsFromService = service.getAll(
                new PagingAndSorting(page > 0 ? page - 1 : page, limit, sort, order));
        if (allTaskListsFromService.size() == 0)
            return ResponseEntity.noContent().build();

        List<TaskListResponse> taskListResponses = allTaskListsFromService.stream()
                .map(taskListDto -> modelMapper.map(taskListDto, TaskListResponse.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<TaskListResponse>> responseCollectionModel =
                assembler.getCollectionModel(taskListResponses, new PagingAndSorting(page, limit, sort, order));

        return new ResponseEntity<>(responseCollectionModel, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<TaskListResponse>> getEntity(@Valid @PathVariable("id") Long id) {

        TaskListDto taskListFromService = service.getById(id);
        if (taskListFromService == null)
            return ResponseEntity.notFound().build();

        TaskListResponse response = modelMapper.map(taskListFromService, TaskListResponse.class);
        EntityModel<TaskListResponse> responseEntityModel = assembler.toModel(response);

        return new ResponseEntity<>(responseEntityModel, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @Override
    public ResponseEntity<EntityModel<TaskListResponse>> updateEntity(@Valid @PathVariable("id") Long id, @RequestBody TaskListRequest updatedRequest) {

        TaskListDto taskListFromService = service.updateById(id, modelMapper.map(updatedRequest, TaskListDto.class));

        TaskListResponse taskListResponse = modelMapper.map(taskListFromService, TaskListResponse.class);
        EntityModel<TaskListResponse> responseEntityModel = assembler.toModel(taskListResponse);

        return new ResponseEntity<>(responseEntityModel, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<?> deleteEntity(@Valid @PathVariable("id") Long id) {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
