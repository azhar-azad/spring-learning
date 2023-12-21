package com.azad.supershop.model.pojo;

import com.azad.supershop.model.constant.ProductUnit;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class SupplyContract {

    @NotBlank
    protected int quantity;

    @NotBlank
    protected ProductUnit unit;

    @NotBlank
    protected LocalDate contractDate;
}
