package com.azad.jsonplaceholderclone.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Todo {

    @NotNull(message = "Todo title cannot be empty")
    @Size(min = 2, max = 30, message = "Todo title length has to be in between 2 to 30 characters")
    private String title;
    private boolean completed;

    public Todo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
