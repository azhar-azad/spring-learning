package com.azad.playstoreapi.models.responses;

import com.azad.playstoreapi.models.pojos.Category;

public class CategoryResponse extends Category {

    private Long id;

    public CategoryResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
