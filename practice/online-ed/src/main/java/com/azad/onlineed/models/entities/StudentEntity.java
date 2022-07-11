package com.azad.onlineed.models.entities;

import com.azad.onlineed.security.entities.UserEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class StudentEntity extends UserEntity {

    private String username;

    public StudentEntity() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
