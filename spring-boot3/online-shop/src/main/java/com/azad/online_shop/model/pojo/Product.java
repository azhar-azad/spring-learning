package com.azad.online_shop.model.pojo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Product {

    @NotBlank(message = "Product name cannot be empty")
    private String productName;
    private String description;
    @NotBlank(message = "Product brand cannot be empty")
    private String brand;
    @NotBlank(message = "Product size information cannot be empty")
    private String size;
    @NotNull(message = "Product price cannot be empty")
    @Min(0)
    private Double price;
}
