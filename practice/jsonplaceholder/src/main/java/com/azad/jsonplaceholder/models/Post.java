package com.azad.jsonplaceholder.models;

import javax.validation.constraints.NotNull;

public class Post {

    @NotNull(message = "Post title cannot be empty")
    private String title;

    @NotNull(message = "Post body cannot be empty")
    private String body;

    public Post() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
