package com.azad.data.models.requests;

import com.azad.data.models.pojos.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class TaskRequest extends Task {

    private Long taskListId;
}
