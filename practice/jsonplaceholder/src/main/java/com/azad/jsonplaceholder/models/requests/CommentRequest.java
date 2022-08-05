package com.azad.jsonplaceholder.models.requests;

import com.azad.jsonplaceholder.models.Comment;

public class CommentRequest extends Comment {

    private Long postId;

    public CommentRequest() {
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
