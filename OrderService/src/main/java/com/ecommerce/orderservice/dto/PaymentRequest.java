package com.ecommerce.orderservice.dto;


public class PaymentRequest {
    private String orderId;
    private double amount;
    private String userId;

    public PaymentRequest() {
    }

    public PaymentRequest(double amount, String orderId, String userId) {
        this.amount = amount;
        this.orderId = orderId;
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
