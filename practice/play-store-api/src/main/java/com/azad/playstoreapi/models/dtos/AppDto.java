package com.azad.playstoreapi.models.dtos;

import com.azad.playstoreapi.models.pojos.App;
import com.azad.playstoreapi.models.pojos.Publisher;

public class AppDto extends App {

    private Long id;

    private Publisher publisher;
    private Long publisherId;

    public AppDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }
}
