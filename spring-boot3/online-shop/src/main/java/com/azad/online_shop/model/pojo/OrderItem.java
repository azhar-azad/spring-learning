package com.azad.online_shop.model.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderItem {

    @NotBlank(message = "Order item quantity cannot be null")
    private Integer quantity;

    @NotBlank(message = "Order item price cannot be null")
    private Double price;
}
