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

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUserDto appUserDto = appUserService.getByEmail(email);

        TaskDto taskDto = modelMapper.map(taskRequest, TaskDto.class);
        taskDto.setUserId(appUserDto.getUserId());

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

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUserDto appUserDto = appUserService.getByEmail(email);

        if (page > 0) page--;
        PagingAndSorting ps = new PagingAndSorting(page, limit, sort, order);

        List<TaskDto> taskDtos = taskService.getAllByUserId(appUserDto.getUserId(), ps);

        if (taskDtos == null || taskDtos.size() == 0) {
            return new ResponseEntity<>(appUtils.getApiResponse(true, "No Task for User", null), HttpStatus.OK);
        }

        List<TaskResponse> taskResponses = taskDtos.stream()
                .map(taskDto -> modelMapper.map(taskDto, TaskResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new ApiResponse(true, "All Tasks for User", taskResponses),
                HttpStatus.OK);
    }

    /**
     * @Name: getTaskByTaskId
     * @Desc: Get a single task by taskId
     * @Route: http://localhost:8080/api/tasks/{taskid}
     * @Method: GET
     * @Authenticated: YES
     * @Authorized: YES (Any logged-in user and admin can get any task they own by taskId)
     * */
    @GetMapping(path = "/{taskId}")
    public ResponseEntity<ApiResponse> getTaskByTaskId(@Valid @PathVariable String taskId) {

        if (authService.notAuthorizedForThisResource(resourceName)) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Unauthorized Request", null),
                    HttpStatus.UNAUTHORIZED);
        }

        appUtils.printControllerMethodInfo("GET", "/api/tasks/"+taskId,
                "getTaskByTaskId", false, "ADMIN, USER");

        // todo: implement how to prevent not owner to get a task
        // first implement without considering owning restriction

        if (taskId == null || taskId.length() == 0) {
            throw new InvalidPathVariableException("No taskId is provided");
        }

        TaskDto taskDto = taskService.getByTaskId(taskId);

        return new ResponseEntity<>(new ApiResponse(true, "Task Fetched By TaskID",
                Collections.singletonList(modelMapper.map(taskDto, TaskResponse.class))),
                HttpStatus.OK);
    }

}
