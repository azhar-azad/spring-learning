package com.azad.jsonplaceholder.models;

import javax.validation.constraints.NotNull;

public class Todo {

    @NotNull(message = "Todo title cannot be empty")
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
