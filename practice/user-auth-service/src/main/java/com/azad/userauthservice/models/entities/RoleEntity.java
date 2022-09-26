package com.azad.userauthservice.models.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    List<AppUserEntity> users = new ArrayList<>();

    public RoleEntity() {
    }

    public Integer getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<AppUserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<AppUserEntity> users) {
        this.users = users;
    }
}
