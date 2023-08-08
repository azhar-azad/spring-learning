package com.azad.taskapiwithauth.services;

import com.azad.taskapiwithauth.commons.PagingAndSorting;
import com.azad.taskapiwithauth.commons.generics.GenericApiService;
import com.azad.taskapiwithauth.models.task.TaskDto;

import java.util.List;

public interface TaskService extends GenericApiService<TaskDto> {

    List<TaskDto> getAllByUser(PagingAndSorting ps);
}
