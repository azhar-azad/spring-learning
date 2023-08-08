package com.azad.simpleprojectmanagement.models.epic;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Epic {

    @NotNull(message = "Epic name cannot be null")
    protected String epicName;

    @NotNull(message = "Epic description cannot be null")
    protected String epicDescription;
}
