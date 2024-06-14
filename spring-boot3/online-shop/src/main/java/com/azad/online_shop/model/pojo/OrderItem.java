package com.azad.online_shop.model.pojo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItem {

    @NotNull(message = "Order item quantity cannot be null")
    @Min(1)
    private Integer quantity;

    @NotNull(message = "Order item price cannot be null")
    @Min(0)
    private Double price;
}
