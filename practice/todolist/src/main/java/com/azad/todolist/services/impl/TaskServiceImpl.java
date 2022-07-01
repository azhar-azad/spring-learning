package com.azad.todolist.services.impl;

import com.azad.todolist.exceptions.ResourceNotFoundException;
import com.azad.todolist.models.AppUser;
import com.azad.todolist.models.dtos.AppUserDto;
import com.azad.todolist.models.dtos.TaskDto;
import com.azad.todolist.models.entities.AppUserEntity;
import com.azad.todolist.models.entities.TaskEntity;
import com.azad.todolist.repos.TaskRepo;
import com.azad.todolist.services.AppUserService;
import com.azad.todolist.services.TaskService;
import com.azad.todolist.utils.AppUtils;
import com.azad.todolist.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUserService appUserService;

    private final TaskRepo taskRepo;

    @Autowired
    public TaskServiceImpl(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Override
    public TaskDto create(TaskDto requestDto) {

        Long appUserId = requestDto.getId();
        AppUserDto ownerDto = appUserService.getByEntityId(appUserId);

        if (requestDto.getDone() == null) {
            requestDto.setDone(false);
        }

        TaskEntity taskEntity = modelMapper.map(requestDto, TaskEntity.class);
        taskEntity.setUser(modelMapper.map(ownerDto, AppUserEntity.class));

        TaskEntity savedTaskEntity = taskRepo.save(taskEntity);

        TaskDto savedTaskDto = modelMapper.map(savedTaskEntity, TaskDto.class);
        savedTaskDto.setUser(modelMapper.map(ownerDto, AppUser.class));
        savedTaskDto.setUserId(ownerDto.getId());

        return savedTaskDto;
    }

    @Override
    public List<TaskDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public TaskDto getByEntityId(Long id) {

        TaskEntity taskEntity = taskRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task", id));

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUserDto appUserDto = appUserService.getByEmail(email);

        if (!Objects.equals(taskEntity.getUser().getId(), appUserDto.getId())) {
            throw new RuntimeException("Not the owner");
        }

        return modelMapper.map(taskEntity, TaskDto.class);
    }

    @Override
    public TaskDto updateByEntityId(Long id, TaskDto updatedDto) {
        return null;
    }

    @Override
    public void deleteByEntityId(Long id) {

    }

    @Override
    public List<TaskDto> getAllByUserId(Long userId, PagingAndSorting ps) {

        Pageable pageable;
        if (ps.getSort() == null || ps.getSort().equals("")) {
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        } else {
            Sort sort = appUtils.getSortBy(ps.getSort(), ps.getOrder());
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), sort);
        }

        AppUserDto ownerDto = appUserService.getByEntityId(userId);

        List<TaskEntity> taskEntities = taskRepo.findByUserId(pageable, ownerDto.getId()).orElse(null);

        if (taskEntities == null || taskEntities.size() == 0)
            return null;

        return taskEntities.stream()
                .map(taskEntity -> modelMapper.map(taskEntity, TaskDto.class))
                .collect(Collectors.toList());
    }
}
