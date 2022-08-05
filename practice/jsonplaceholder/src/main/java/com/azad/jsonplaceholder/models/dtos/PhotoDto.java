package com.azad.jsonplaceholder.models.dtos;

import com.azad.jsonplaceholder.models.Album;
import com.azad.jsonplaceholder.models.Photo;

public class PhotoDto extends Photo {

    private Long id;
    private Album album;
    private Long albumId;

    public PhotoDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
}
