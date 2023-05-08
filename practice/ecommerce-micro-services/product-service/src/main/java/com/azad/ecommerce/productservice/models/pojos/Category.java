package com.azad.ecommerce.productservice.models.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class Category {

    @NotNull(message = "Category name cannot be empty")
    protected String categoryName;
}
