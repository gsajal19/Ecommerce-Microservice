package com.example.PAYMENTSERVICE.dto;

import org.springframework.stereotype.Repository;


public class RequestVerifyPayment {
    private String orderId;
    private String paymentId;
    private String signature;

    public RequestVerifyPayment() {
    }

    public RequestVerifyPayment(String orderId, String paymentId, String signature) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.signature = signature;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
