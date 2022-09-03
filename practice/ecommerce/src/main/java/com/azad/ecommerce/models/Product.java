package com.azad.ecommerce.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Product {

    @NotNull(message = "Product name cannot be empty")
    @Size(min = 2, max = 50, message = "Product name length has to be between 2 to 50 characters.")
    private String productName;

    @NotNull(message = "Product description cannot be empty")
    @Size(min = 2, max = 200, message = "Product description length has to be between 2 to 50 characters.")
    private String description;

    @NotNull(message = "Product price cannot be empty")
    @Size(min = 1, max = 20, message = "Product price length has to be between 1 to 20 characters.")
    private String price;

    private LocalDate createdDate;
    private LocalDateTime modifiedTime;

    public Product() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
