package com.azad.taskapiwithauth.services;

import com.azad.taskapiwithauth.commons.PagingAndSorting;
import com.azad.taskapiwithauth.commons.exceptions.ResourceNotFoundException;
import com.azad.taskapiwithauth.commons.utils.ApiUtils;
import com.azad.taskapiwithauth.models.auth.AppUserEntity;
import com.azad.taskapiwithauth.models.task.Task;
import com.azad.taskapiwithauth.models.task.TaskDto;
import com.azad.taskapiwithauth.models.task.TaskEntity;
import com.azad.taskapiwithauth.repositories.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiUtils apiUtils;

    @Autowired
    private AuthService authService;

    private final TaskRepository repository;

    @Autowired
    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public TaskDto create(TaskDto dto) {

        TaskEntity task = modelMapper.map(dto, TaskEntity.class);
        task.setCreatedAt(LocalDateTime.now());
        task.setUser(authService.getLoggedInUser());

        TaskEntity savedTask = repository.save(task);

        return modelMapper.map(savedTask, TaskDto.class);
    }

    @Override
    public List<TaskDto> getAllByUser(PagingAndSorting ps) {

        AppUserEntity loggedInUser = authService.getLoggedInUser();

        if (loggedInUser.getTasks().isEmpty()) {
            return null;
        }

        return loggedInUser.getTasks().stream()
                .map(taskEntity -> modelMapper.map(taskEntity, TaskDto.class))
                .collect(Collectors.toList());

//        Optional<List<TaskEntity>> byUserId =
//                repository.findByUserId(loggedInUser.getId(), apiUtils.getPageable(ps));
//
//        List<TaskEntity> taskEntities = null;
//        if (byUserId.isPresent())
//            taskEntities = byUserId.get();
//
//        if (taskEntities == null || taskEntities.isEmpty())
//            return null;
//
//        return taskEntities.stream()
//                .map(taskEntity -> modelMapper.map(taskEntity, TaskDto.class))
//                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getAll(PagingAndSorting ps) {
        throw new RuntimeException("Method should not be used");
    }

    @Override
    public TaskDto getById(Long id) {

        AppUserEntity loggedInUser = authService.getLoggedInUser();

        List<TaskEntity> tasks = loggedInUser.getTasks().stream()
                .filter(taskEntity -> Objects.equals(taskEntity.getId(), id)).toList();

        if (tasks.isEmpty())
            throw new ResourceNotFoundException("Resource not found");
        if (tasks.size() > 1)
            throw new RuntimeException("How can there be two tasks with same taskId. Please investigate");

        return modelMapper.map(tasks.get(0), TaskDto.class);
    }

    @Override
    public TaskDto updateById(Long id, TaskDto updatedDto) {

        AppUserEntity loggedInUser = authService.getLoggedInUser();

        List<TaskEntity> tasks = loggedInUser.getTasks().stream()
                .filter(taskEntity -> Objects.equals(taskEntity.getId(), id)).toList();

        if (tasks.isEmpty())
            throw new ResourceNotFoundException("Resource not found");
        if (tasks.size() > 1)
            throw new RuntimeException("How can there be two tasks with same taskId. Please investigate");

        TaskEntity task = tasks.get(0);
        if (updatedDto.getTaskName() != null)
            task.setTaskName(updatedDto.getTaskName());
        if (updatedDto.getDueDate() != null)
            task.setDueDate(updatedDto.getDueDate());
        task.setModifiedAt(LocalDateTime.now());

        TaskEntity updatedTask = repository.save(task);

        return modelMapper.map(updatedTask, TaskDto.class);
    }

    @Override
    public void deleteById(Long id) {

        AppUserEntity loggedInUser = authService.getLoggedInUser();

        List<TaskEntity> tasks = loggedInUser.getTasks().stream()
                .filter(taskEntity -> Objects.equals(taskEntity.getId(), id)).toList();

        if (tasks.isEmpty())
            throw new RuntimeException("Task not found with id: " + id);
        if (tasks.size() > 1)
            throw new RuntimeException("How can there be two tasks with same taskId. Please investigate");

        TaskEntity task = tasks.get(0);

        loggedInUser.getTasks().remove(task);
        repository.delete(task);
    }
}
