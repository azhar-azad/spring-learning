package com.azad.jsonplaceholder.models;

import javax.validation.constraints.NotNull;

public class Comment {

    private String name;
    private String email;

    @NotNull(message = "Comment body cannot be empty")
    private String body;

    public Comment() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
