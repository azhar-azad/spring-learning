package com.azad.data.models.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class TaskList {

    @NotNull(message = "List title cannot be empty.")
    protected String listTitle;
    protected String listDetails;
}
