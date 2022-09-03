package com.azad.ecommerce.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class PaymentMethod {

    @NotNull(message = "Payment method name cannot be empty")
    @Size(min = 2, max = 50, message = "Payment method name length has to be between 2 to 50 characters.")
    private String methodName;

    @NotNull(message = "Payment type cannot be empty")
    @Size(min = 2, max = 50, message = "Payment type length has to be between 2 to 50 characters.")
    private String paymentType;

    private LocalDateTime transactionTime;

    public PaymentMethod() {
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
}
