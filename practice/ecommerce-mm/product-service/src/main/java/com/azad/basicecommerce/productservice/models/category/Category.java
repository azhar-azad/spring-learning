package com.azad.basicecommerce.productservice.models.category;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class Category {

    protected String categoryUid;

    @NotNull(message = "Category name must be provided")
    protected String categoryName;
}
