package com.azad.jsonplaceholderclone.models.responses;

import com.azad.jsonplaceholderclone.models.Comment;

public class CommentResponse extends Comment {

    private Long id;
    private Long postId;

    public CommentResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
