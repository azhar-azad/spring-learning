package com.azad.online_shop.model.pojo;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Product price cannot be empty")
    private Double price;
}
