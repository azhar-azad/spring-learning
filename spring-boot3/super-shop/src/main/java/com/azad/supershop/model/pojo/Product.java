package com.azad.supershop.model.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Product {

    @NotBlank
    protected String productName;

    @NotBlank
    protected double price;

    @NotBlank
    protected int stockQuantity;
}
