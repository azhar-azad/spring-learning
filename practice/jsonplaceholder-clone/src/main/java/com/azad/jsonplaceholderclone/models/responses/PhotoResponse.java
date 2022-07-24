package com.azad.jsonplaceholderclone.models.responses;

import com.azad.jsonplaceholderclone.models.Photo;

public class PhotoResponse extends Photo {

    private Long id;
    private Long albumId;

    public PhotoResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
}
