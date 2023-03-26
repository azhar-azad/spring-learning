package com.azad.data.models.responses;

import com.azad.data.models.pojos.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class TaskResponse extends Task {

    private Long id;
    private Long taskListId;
}
