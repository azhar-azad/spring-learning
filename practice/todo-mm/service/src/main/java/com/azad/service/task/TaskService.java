package com.azad.service.task;

import com.azad.common.PagingAndSorting;
import com.azad.data.models.dtos.TaskDto;
import com.azad.service.GenericApiService;

import java.util.List;

public interface TaskService extends GenericApiService<TaskDto> {

    List<TaskDto> getAllByList(Long taskListId, PagingAndSorting ps);
}
