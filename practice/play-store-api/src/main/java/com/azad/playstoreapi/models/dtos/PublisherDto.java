package com.azad.playstoreapi.models.dtos;

import com.azad.playstoreapi.models.pojos.Publisher;

public class PublisherDto extends Publisher {

    private Long id;

    public PublisherDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
