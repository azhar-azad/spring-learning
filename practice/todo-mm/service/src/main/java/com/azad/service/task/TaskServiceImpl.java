package com.azad.service.task;

import com.azad.common.AppUtils;
import com.azad.common.PagingAndSorting;
import com.azad.data.models.dtos.TaskDto;
import com.azad.data.models.entities.AccessEntity;
import com.azad.data.models.entities.AppUserEntity;
import com.azad.data.models.entities.TaskEntity;
import com.azad.data.models.entities.TaskListEntity;
import com.azad.data.repos.AccessRepository;
import com.azad.data.repos.TaskListRepository;
import com.azad.data.repos.TaskRepository;
import com.azad.security.auth.AuthService;
import com.azad.service.ServiceUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ServiceUtils serviceUtils;

    @Autowired
    private AuthService authService;

    @Autowired
    private TaskListRepository taskListRepository;

    private final TaskRepository repository;

    @Autowired
    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public TaskDto create(TaskDto dto) {

        Long taskListId = dto.getTaskListId();
        TaskListEntity taskList = taskListRepository.findById(taskListId).orElseThrow(
                () -> new RuntimeException("TaskList not found with id: " + taskListId));

        AppUserEntity loggedInUser = authService.getLoggedInUser();

        String loggedInUserAccess = serviceUtils.getLoggedInUserAccess(loggedInUser, taskList);
        if (loggedInUserAccess == null || loggedInUserAccess.equalsIgnoreCase("READ")) {
            throw new RuntimeException("User is not authorized to add new task to this list");
        }

        TaskEntity task = modelMapper.map(dto, TaskEntity.class);
        task.setTaskList(taskList);

        TaskEntity savedTask = repository.save(task);

        return modelMapper.map(savedTask, TaskDto.class);
    }

    @Override
    public List<TaskDto> getAll(PagingAndSorting ps) {

        AppUserEntity loggedInUser = authService.getLoggedInUser();

        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        List<TaskEntity> taskEntities = repository.findAll(pageable).getContent();
        if (taskEntities.size() == 0)
            return null;

        return taskEntities.stream()
                .map(taskEntity -> modelMapper.map(taskEntity, TaskDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getAllByList(Long taskListId, PagingAndSorting ps) {

        AppUserEntity loggedInUser = authService.getLoggedInUser();

        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        Optional<List<TaskEntity>> optionalTaskEntities = repository.findByTaskList(taskListId, pageable);
        if (!optionalTaskEntities.isPresent())
            return null;

        List<TaskEntity> taskEntities = optionalTaskEntities.get();



        return taskEntities.stream()
                .map(taskEntity -> modelMapper.map(taskEntity, TaskDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto getById(Long id) {
        return null;
    }

    @Override
    public TaskDto updateById(Long id, TaskDto updatedDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
