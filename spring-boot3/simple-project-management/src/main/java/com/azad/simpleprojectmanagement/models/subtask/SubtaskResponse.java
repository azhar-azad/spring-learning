package com.azad.simpleprojectmanagement.models.subtask;

import com.azad.simpleprojectmanagement.models.task.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SubtaskResponse extends Subtask {

    private Long id;
    private Task task;
}
