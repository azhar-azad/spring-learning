package com.azad.jsonplaceholder.models.responses;

import com.azad.jsonplaceholder.models.Post;

public class PostResponse extends Post {

    private Long id;
    private Long userId;

    public PostResponse() {
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
