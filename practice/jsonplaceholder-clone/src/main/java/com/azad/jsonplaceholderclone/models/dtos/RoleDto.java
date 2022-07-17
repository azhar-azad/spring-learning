package com.azad.jsonplaceholderclone.models.dtos;

import com.azad.jsonplaceholderclone.models.Role;

public class RoleDto extends Role {

    private Integer id;

    public RoleDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
