package com.azad.simpleprojectmanagement.models.subtask;

import com.azad.simpleprojectmanagement.models.task.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SubtaskDto extends Subtask {

    private Long taskId;

    private Long id;
    private Task task;
}
