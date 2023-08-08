package com.azad.simpleprojectmanagement.models.subtask;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Subtask {

    @NotNull(message = "Subtask summary cannot be null")
    protected String subtaskSummary;

    protected String subtaskDescription;
}
