package com.azad.jsonplaceholder.models.responses;

import com.azad.jsonplaceholder.models.Album;

public class AlbumResponse extends Album {

    private Long id;
    private Long userId;

    public AlbumResponse() {
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
