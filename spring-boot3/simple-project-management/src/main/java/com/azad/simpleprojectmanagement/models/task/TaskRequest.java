package com.azad.simpleprojectmanagement.models.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TaskRequest extends Task {

    private Long storyId;
}
