package com.azad.basicecommerce.productservice.models.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class Product {

    protected String productUid;

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

    @NotNull(message = "Must provide estimated product delivery time in days")
    protected String estimatedDeliveryTime;

    @NotNull(message = "Must provide how many are in stock")
    protected Integer availableInStock;

    @NotNull(message = "Must provide the low amount of stock threshold")
    protected Integer lowStockThreshold;

    protected Double discount;
    protected String returnPolicy;
    protected String warranty;
    protected Double averageRating;
    protected Long totalReview;
}
