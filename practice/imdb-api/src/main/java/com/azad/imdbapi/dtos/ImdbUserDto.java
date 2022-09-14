package com.azad.imdbapi.dtos;

import com.azad.imdbapi.models.ImdbUser;

public class ImdbUserDto extends ImdbUser {

    private Long id;
    private String roleName;

    public ImdbUserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
