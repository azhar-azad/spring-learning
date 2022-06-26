package com.azad.todolist.services.impl;

import com.azad.todolist.models.dtos.TaskDto;
import com.azad.todolist.repos.TaskRepo;
import com.azad.todolist.services.TaskService;
import com.azad.todolist.utils.PagingAndSorting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;

    @Autowired
    public TaskServiceImpl(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Override
    public TaskDto create(TaskDto requestDto) {
        return null;
    }

    @Override
    public List<TaskDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public TaskDto getByEntityId(String entityId) {
        return null;
    }

    @Override
    public TaskDto updateByEntityId(String entityId, TaskDto updatedDto) {
        return null;
    }

    @Override
    public void deleteByEntityId(String entityId) {

    }
}
