package com.azad.imdbapi.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Person {

    @NotNull(message = "Person name cannot be empty")
    @Size(min = 1, message = "Person name must be of at least 1 character")
    private String personName;

    public Person() {
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
