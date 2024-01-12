package com.azad.budget;

import lombok.Data;

import java.util.HashSet;
import java.util.Optional;

@Data
public class Builder {

    private final Budget budget;

    public Builder() {
        budget = new Budget(new HashSet<>(), 0.0, 0.0, 0.0, 0.0);
    }

    public Budget addCategory(Category category) {
        budget.getCategories().add(category);
        return budget;
    }

    public Budget addSubCategory(SubCategory subCategory, Category category) {
        Optional<Category> optionalCategory = budget.getCategories().stream()
                .filter(c -> c.getName().equals(category.getName())).findFirst();
        if (optionalCategory.isEmpty())
            throw new RuntimeException("Category not found with name: " + category.getName());
        optionalCategory.get().getSubCategories().add(subCategory);
        return budget;
    }
}
