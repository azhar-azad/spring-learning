package com.azad.ecommerce.productservice.models.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class Product {

    @NotNull(message = "Product name can not be empty")
    protected String productName;

    @NotNull(message = "Product brand can not be empty")
    protected String brand;

    @NotNull(message = "Product description can not be empty")
    protected String description;

    @NotNull(message = "Product picture must be given")
    protected String pictureUrl;

    @NotNull(message = "Product price can not be empty")
    @Min(0)
    protected Double price;

    protected Double discount;

    @NotNull(message = "Must provide estimated product delivery time")
    protected String estimatedDeliveryTime;

    protected String returnPolicy;
    protected String warranty;

    @NotNull(message = "Must provide how many are in stock")
    protected Integer availableInStock;

    @NotNull(message = "Must provide the low amount of stock threshold")
    protected Integer lowStockThreshold;

    protected Double averageRating;
    protected Long totalReview;
}
