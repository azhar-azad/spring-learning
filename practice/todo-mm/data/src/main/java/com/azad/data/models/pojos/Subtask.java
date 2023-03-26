package com.azad.data.models.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * Task can have one or more Subtask.
 * One Task many Subtasks.
 */

@Data
@NoArgsConstructor
public class Subtask {

    protected String subtaskTitle;
}
