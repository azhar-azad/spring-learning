package com.azad.todolist.controllers;

import com.azad.todolist.exceptions.InvalidPathVariableException;
import com.azad.todolist.models.Task;
import com.azad.todolist.models.dtos.AppUserDto;
import com.azad.todolist.models.dtos.TaskDto;
import com.azad.todolist.models.responses.ApiResponse;
import com.azad.todolist.models.responses.TaskResponse;
import com.azad.todolist.services.AppUserService;
import com.azad.todolist.services.AuthService;
import com.azad.todolist.services.TaskService;
import com.azad.todolist.utils.AppUtils;
import com.azad.todolist.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/tasks")
public class TaskController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AuthService authService;

    private final TaskService taskService;
    private final String resourceName;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
        this.resourceName = "Task";
    }

    /**
     * @Name: createTask
     * @Desc: Create a new task for the logged-in user
     * @Route: http://localhost:8080/api/tasks
     * @Method: POST
     * @Authenticated: YES
     * @Authorized: NO (Any logged-in user and admin can create a task)
     * */
    @PostMapping
    public ResponseEntity<ApiResponse> createTask(@Valid @RequestBody Task taskRequest) {

        appUtils.printControllerMethodInfo("POST", "/api/tasks",
                "createTask", false, "ADMIN, USER");

        if (authService.notAuthorizedForThisResource(resourceName)) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Unauthorized Request", null),
                    HttpStatus.UNAUTHORIZED);
        }

        TaskDto taskDto = modelMapper.map(taskRequest, TaskDto.class);

        TaskDto savedTaskDto = taskService.create(taskDto);

        TaskResponse taskResponse = modelMapper.map(savedTaskDto, TaskResponse.class);

        return new ResponseEntity<>(new ApiResponse(true, "Task Created",
                Collections.singletonList(taskResponse)),
                HttpStatus.CREATED);
    }

    /**
     * @Name: getAllTasks
     * @Desc: Get all tasks for a user
     * @Route: http://localhost:8080/api/tasks
     * @Method: GET
     * @Authenticated: YES
     * @Authorized: NO (Any logged-in user and admin can view all of his tasks)
     * */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllTasks(
           @Valid @RequestParam(value = "page", defaultValue = "1") int page,
           @Valid @RequestParam(value = "limit", defaultValue = "25") int limit,
           @Valid @RequestParam(value = "sort", defaultValue = "") String sort,
           @Valid @RequestParam(value = "order", defaultValue = "asc") String order) {

        if (authService.notAuthorizedForThisResource(resourceName)) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Unauthorized Request", null),
                    HttpStatus.UNAUTHORIZED);
        }

        appUtils.printControllerMethodInfo("GET", "/api/tasks",
                "getAllTasks", false, "ADMIN, USER");

        if (page > 0) page--;
        PagingAndSorting ps = new PagingAndSorting(page, limit, sort, order);

        List<TaskDto> taskDtos = taskService.getAll(ps);

        if (taskDtos == null || taskDtos.size() == 0) {
            return new ResponseEntity<>(appUtils.getApiResponse(true, "No Task for User", null),
                    HttpStatus.OK);
        }

        List<TaskResponse> taskResponses = taskDtos.stream()
                .map(taskDto -> modelMapper.map(taskDto, TaskResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new ApiResponse(true, "All Tasks for User", taskResponses),
                HttpStatus.OK);
    }

    /**
     * @Name: getTaskById
     * @Desc: Get a single task by entity id
     * @Route: http://localhost:8080/api/tasks/{id}
     * @Method: GET
     * @Authenticated: YES
     * @Authorized: YES (Any logged-in user and admin can get any task by taskId. No owning restriction)
     * */
    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> getTaskById(@Valid @PathVariable Long id) {

        if (authService.notAuthorizedForThisResource(resourceName)) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Unauthorized Request", null),
                    HttpStatus.UNAUTHORIZED);
        }

        appUtils.printControllerMethodInfo("GET", "/api/tasks/"+id,
                "getTaskById", false, "ADMIN, USER");

        if (id == null || id <= 0) {
            throw new InvalidPathVariableException("Provided ID is invalid: " + id);
        }

        TaskDto taskDto = taskService.getByEntityId(id);

        return new ResponseEntity<>(new ApiResponse(true, "Task Fetched By TaskID",
                Collections.singletonList(modelMapper.map(taskDto, TaskResponse.class))),
                HttpStatus.OK);
    }

    /**
     * @Name: updateTaskById
     * @Desc: Update a single task by entity id
     * @Route: http://localhost:8080/api/tasks/{id}
     * @Method: PUT
     * @Authenticated: YES
     * @Authorized: YES (Any logged-in user and admin can get any task they own by taskId)
     * */
    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> updateTaskById(@Valid @PathVariable Long id, @RequestBody Task updateRequest) {

        if (authService.notAuthorizedForThisResource(resourceName)) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Unauthorized Request", null),
                    HttpStatus.UNAUTHORIZED);
        }

        appUtils.printControllerMethodInfo("PUT", "/api/tasks/"+id,
                "updateTaskById", false, "ADMIN, USER");

        if (id == null || id <= 0) {
            throw new InvalidPathVariableException("Provided ID is invalid: " + id);
        }

        TaskDto updatedTaskDto = taskService.updateByEntityId(id, modelMapper.map(updateRequest, TaskDto.class));

        return new ResponseEntity<>(new ApiResponse(true, "Task Updated By TaskID",
                Collections.singletonList(modelMapper.map(updatedTaskDto, TaskResponse.class))),
                HttpStatus.OK);
    }

    /**
     * @Name: deleteTaskById
     * @Desc: Delete a single task by entity id
     * @Route: http://localhost:8080/api/tasks/{id}
     * @Method: DELETE
     * @Authenticated: YES
     * @Authorized: YES (Any logged-in user and admin can get any task they own by taskId)
     * */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteTaskById(@Valid @PathVariable Long id) {

        if (authService.notAuthorizedForThisResource(resourceName)) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Unauthorized Request", null),
                    HttpStatus.UNAUTHORIZED);
        }

        appUtils.printControllerMethodInfo("PUT", "/api/tasks/"+id,
                "updateTaskById", false, "ADMIN, USER");

        if (id == null || id <= 0) {
            throw new InvalidPathVariableException("Provided ID is invalid: " + id);
        }

        taskService.deleteByEntityId(id);

        return ResponseEntity.noContent().build();
    }
}
