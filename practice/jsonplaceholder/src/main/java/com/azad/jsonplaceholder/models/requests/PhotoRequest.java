package com.azad.jsonplaceholder.models.requests;

import com.azad.jsonplaceholder.models.Photo;

public class PhotoRequest extends Photo {

    private Long albumId;

    public PhotoRequest() {
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
}
