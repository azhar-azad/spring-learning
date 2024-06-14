package com.azad.online_shop.model.pojo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Inventory {

    @NotNull(message = "Product count on inventory cannot be empty")
    @Min(1)
    private Integer quantity;
}
