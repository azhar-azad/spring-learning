package com.azad.data.models.responses;

import com.azad.data.models.pojos.Task;
import com.azad.data.models.pojos.TaskList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TaskListResponse extends TaskList {

    private Long id;
    private List<Task> tasks;
}
