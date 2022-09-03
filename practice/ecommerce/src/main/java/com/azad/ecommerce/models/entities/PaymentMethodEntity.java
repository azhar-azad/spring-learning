package com.azad.ecommerce.models.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_method")
public class PaymentMethodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String methodName;

    @Column(nullable = false)
    private String paymentType;

    @Column(nullable = false)
    private LocalDateTime transactionTime;

    @OneToOne(mappedBy = "paymentMethod")
    private OrderEntity order;

    public PaymentMethodEntity() {
    }

    public Long getId() {
        return id;
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

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
