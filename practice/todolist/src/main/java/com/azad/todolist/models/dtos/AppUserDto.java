package com.azad.todolist.models.dtos;

import com.azad.todolist.models.AppUser;

public class AppUserDto extends AppUser {

    private Long id;

    public AppUserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
