package com.azad.jsonplaceholderclone.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Post {

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
