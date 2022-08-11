package com.azad.jsonplaceholder.models.responses;

import com.azad.jsonplaceholder.models.Todo;

public class TodoResponse extends Todo {

    private Long id;
    private Long userId;

    public TodoResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
