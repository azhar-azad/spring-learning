package com.azad.supershop.model.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Customer {

    @NotBlank
    protected String customerName;

    @NotBlank
    protected String phone;

    protected String email;

    @NotBlank
    protected String address;

    protected double lifetimeBuyAmount;
    protected Long totalTransactionCount;
    protected double customerValue;
    protected double totalDue;
}
