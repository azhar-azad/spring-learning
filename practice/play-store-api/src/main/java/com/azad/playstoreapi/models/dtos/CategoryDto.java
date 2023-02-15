package com.azad.playstoreapi.models.dtos;

import com.azad.playstoreapi.models.pojos.Category;

public class CategoryDto extends Category {

    private Long id;

    public CategoryDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
