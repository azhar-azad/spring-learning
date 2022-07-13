package com.azad.onlineed.models.entities;

import com.azad.onlineed.security.entities.UserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "instructor")
public class InstructorEntity extends UserEntity {

    @Column(name = "ins_username")
    private String username;

    @Column(name = "exp_in_years")
    private int experience;

    @Column
    private double rating = 0;

    public InstructorEntity() {
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
