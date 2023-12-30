package com.azad.supershop.model.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class Cart {

    protected LocalDate date;
    protected double totalPrice;
}
