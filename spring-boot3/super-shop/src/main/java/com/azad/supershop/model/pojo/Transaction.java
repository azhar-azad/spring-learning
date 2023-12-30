package com.azad.supershop.model.pojo;

import com.azad.supershop.model.constant.PaymentType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class Transaction {

    @NotBlank
    protected LocalDate date;

    @NotBlank
    protected double totalAmount;

    @NotBlank
    protected double paidAmount;

    protected double dueAmount;

    @NotBlank
    protected PaymentType paymentMethod;

    protected String receiverName;
}
