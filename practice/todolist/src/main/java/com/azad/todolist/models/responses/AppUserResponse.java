package com.azad.todolist.models.responses;

import com.azad.todolist.models.AppUser;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppUserResponse extends AppUser {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
