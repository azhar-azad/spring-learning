package com.azad.data.models.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/***
 * Task to represent a single todo.
 */
@Data
@NoArgsConstructor
public class Task {

    @NotNull(message = "Task title cannot be empty.")
    @Size(min = 2, message = "Task title has to be more than 2 characters long.")
    protected String taskTitle;
    protected String taskDetails;
    protected LocalDateTime targetDateTime;
    protected boolean isStarred;
    protected boolean isPrivate;
}
