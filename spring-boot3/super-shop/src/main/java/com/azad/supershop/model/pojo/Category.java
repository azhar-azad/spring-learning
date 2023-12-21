package com.azad.supershop.model.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Category {

    @NotBlank
    protected String categoryName;

    protected String description;
}
