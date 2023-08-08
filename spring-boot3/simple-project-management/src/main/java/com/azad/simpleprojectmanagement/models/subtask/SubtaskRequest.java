package com.azad.simpleprojectmanagement.models.subtask;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SubtaskRequest extends Subtask {

    private Long taskId;
}
