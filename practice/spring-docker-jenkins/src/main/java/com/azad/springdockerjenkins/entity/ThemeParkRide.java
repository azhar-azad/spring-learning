package com.azad.springdockerjenkins.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class ThemeParkRide {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @Column
    private int thrillFactor;

    @Column
    private int vomitFactor;

    public ThemeParkRide() {
    }

    public ThemeParkRide(String name, String description, int thrillFactor, int vomitFactor) {
        this.name = name;
        this.description = description;
        this.thrillFactor = thrillFactor;
        this.vomitFactor = vomitFactor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getThrillFactor() {
        return thrillFactor;
    }

    public void setThrillFactor(int thrillFactor) {
        this.thrillFactor = thrillFactor;
    }

    public int getVomitFactor() {
        return vomitFactor;
    }

    public void setVomitFactor(int vomitFactor) {
        this.vomitFactor = vomitFactor;
    }
}
