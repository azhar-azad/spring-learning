package com.azad.todolist.models.dtos;

import com.azad.todolist.models.AppUser;
import com.azad.todolist.models.Task;

public class TaskDto extends Task {

    private Long id;
    private AppUser user;
    private String userId;

    public TaskDto() {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
