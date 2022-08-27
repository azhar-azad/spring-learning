package com.azad.ListShare.models.dtos;

import com.azad.ListShare.models.Role;

public class RoleDto extends Role {

    private Long id;

    public RoleDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
