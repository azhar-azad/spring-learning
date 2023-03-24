package com.azad.model.doctor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Doctor {

    @Id
    private Long id;
    private String name;
    private String specialized;

    public Doctor() {
    }

    public Doctor(Long id, String name, String specialized) {
        this.id = id;
        this.name = name;
        this.specialized = specialized;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialized() {
        return specialized;
    }

    public void setSpecialized(String specialized) {
        this.specialized = specialized;
    }
}
