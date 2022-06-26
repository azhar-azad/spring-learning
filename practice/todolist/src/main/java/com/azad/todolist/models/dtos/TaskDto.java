package com.azad.todolist.models.dtos;

import com.azad.todolist.models.AppUser;
import com.azad.todolist.models.Task;

public class TaskDto extends Task {

    private Long id;
    private AppUser appUser;

    public TaskDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
