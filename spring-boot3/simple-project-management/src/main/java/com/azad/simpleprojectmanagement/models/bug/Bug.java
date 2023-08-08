package com.azad.simpleprojectmanagement.models.bug;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Bug {

    @NotNull(message = "Bug summary cannot be null")
    protected String bugSummary;

    protected String bugDescription;
}
