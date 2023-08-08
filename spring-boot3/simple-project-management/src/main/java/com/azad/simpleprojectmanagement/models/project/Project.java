package com.azad.simpleprojectmanagement.models.project;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Project {

    @NotNull(message = "Project name cannot be null")
    protected String projectName;

    @NotNull(message = "Project description cannot be null")
    protected String description;
}
