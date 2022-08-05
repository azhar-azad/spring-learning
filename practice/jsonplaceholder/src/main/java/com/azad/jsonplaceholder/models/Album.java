package com.azad.jsonplaceholder.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Album {

    @NotNull(message = "Album title cannot be empty")
    @Size(min = 2, max = 20, message = "Album title length has to be in between 2 to 20 characters")
    private String title;

    public Album() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
