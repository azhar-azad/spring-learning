package com.azad.simpleprojectmanagement.models.task;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Task {

    @NotNull(message = "Task summary cannot be null")
    protected String taskSummary;

    protected String taskDescription;
}
