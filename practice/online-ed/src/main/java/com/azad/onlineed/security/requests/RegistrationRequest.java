package com.azad.onlineed.security.requests;

import com.azad.onlineed.models.User;

import java.util.Set;

public class RegistrationRequest extends User {

    private Set<String> roleNames;

    private String username; // for both student and instructor entity

    private int experience; // for instructor entity

    public RegistrationRequest() {
    }

    public Set<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(Set<String> roleNames) {
        this.roleNames = roleNames;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
