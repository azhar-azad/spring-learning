package com.azad.supershop.model.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Product {

    @NotBlank
    protected String productName;

    @NotNull(message = "Product price cannot be null")
    protected Double price;

    @NotNull(message = "Stock quantity cannot be null")
    protected Integer stockQuantity;
}
