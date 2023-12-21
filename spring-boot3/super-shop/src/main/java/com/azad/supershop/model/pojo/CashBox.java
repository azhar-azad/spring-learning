package com.azad.supershop.model.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CashBox {

    @NotBlank
    protected String boxName;

    @NotBlank
    protected double initialAmount;

    protected double currentAmount;
    protected long transactionCount;
}
