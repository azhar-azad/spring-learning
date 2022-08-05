package com.azad.jsonplaceholder.models.dtos;

import com.azad.jsonplaceholder.models.Role;

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
