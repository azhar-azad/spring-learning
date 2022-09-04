package com.azad.ecommerce.models.dtos;

import com.azad.ecommerce.models.User;

public class UserDto extends User {

    private Long id;

    private String roleName;

    public UserDto() {

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
