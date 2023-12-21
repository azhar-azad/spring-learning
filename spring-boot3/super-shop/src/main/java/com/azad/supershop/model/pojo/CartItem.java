package com.azad.supershop.model.pojo;

import com.azad.supershop.model.constant.ProductUnit;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CartItem {

    @NotBlank
    protected int quantity;

    @NotBlank
    @Enumerated(EnumType.STRING)
    protected ProductUnit unit;

    protected double subtotalPrice;
}
