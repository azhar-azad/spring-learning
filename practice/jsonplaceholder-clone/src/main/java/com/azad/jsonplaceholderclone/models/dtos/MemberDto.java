package com.azad.jsonplaceholderclone.models.dtos;

import com.azad.jsonplaceholderclone.models.*;

import java.util.List;

public class MemberDto extends Member {

    private Long id;
    private Role role;
    private String roleName;

    private List<Todo> todos;

    public MemberDto() {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }
}
