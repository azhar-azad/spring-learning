package com.azad.todolist.services;

import com.azad.todolist.models.dtos.TaskDto;
import com.azad.todolist.utils.PagingAndSorting;

import java.util.List;

public interface TaskService extends ApiService<TaskDto> {
    List<TaskDto> getAllByUserId(String userId, PagingAndSorting ps);

    TaskDto getByTaskId(String taskId);
}
