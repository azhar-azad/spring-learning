package com.azad.jsonplaceholderclone.models.requests;

import com.azad.jsonplaceholderclone.models.Photo;

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
