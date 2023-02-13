package com.azad.playstoreapi.models.dtos;

import com.azad.playstoreapi.models.pojos.PlayStoreUser;
import com.azad.playstoreapi.models.pojos.Role;

public class PlayStoreUserDto extends PlayStoreUser {

    private Long id;
    private Role role;
    private String roleName;

    public PlayStoreUserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
