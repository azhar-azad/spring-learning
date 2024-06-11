package com.azad.online_shop.model.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Inventory {

    @NotBlank(message = "Product count on inventory cannot be empty")
    private Integer quantity;
}
