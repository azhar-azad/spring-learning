package com.azad.todolist.models.responses;

import com.azad.todolist.models.AppUser;
import com.azad.todolist.models.Task;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskResponse extends Task {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    private AppUser user;

    public TaskResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
