package com.azad.jsonplaceholder.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Photo {

    @NotNull(message = "Photo title cannot be empty")
    @Size(min = 2, max = 20, message = "Photo title length has to be in between 2 to 20 characters")
    private String title;

    @NotNull(message = "Photo URL cannot be empty")
    private String url;
    private String thumbnailUrl;

    public Photo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
