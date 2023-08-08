package com.azad.taskapiwithauth.models.task;

import com.azad.taskapiwithauth.models.auth.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TaskResponse extends Task {

    private Long id;
    private AppUser user;
}
