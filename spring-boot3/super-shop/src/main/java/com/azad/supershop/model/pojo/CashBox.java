package com.azad.supershop.model.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CashBox {

    @NotBlank
    protected String boxName;

    @NotNull(message = "Initial amount cannot be empty")
    protected Double initialAmount;

    protected Double currentAmount;
    protected Long transactionCount;
}
