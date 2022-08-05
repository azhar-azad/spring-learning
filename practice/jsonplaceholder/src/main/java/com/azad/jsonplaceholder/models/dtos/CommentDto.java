package com.azad.jsonplaceholder.models.dtos;

import com.azad.jsonplaceholder.models.Comment;
import com.azad.jsonplaceholder.models.Post;

public class CommentDto extends Comment {

    private Long id;
    private Post post;
    private Long postId;

    public CommentDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
