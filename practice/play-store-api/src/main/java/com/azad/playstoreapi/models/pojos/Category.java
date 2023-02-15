package com.azad.playstoreapi.models.pojos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Category {

    @NotNull(message = "Category name cannot be empty")
    @Size(min = 2, max = 50, message = "Category name length has to be between 2 to 50 characters.")
    protected String categoryName;

    public Category() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
