package com.ecommerce.orderservice.dto;

import jakarta.persistence.Entity;

public class TotalCharges {
    private float totalPrice;
    private float tax;
    private float deliveryCharges;
    private float totalPayableCharges;

    public TotalCharges() {
    }

    public TotalCharges(float totalPrice, float tax, float deliveryCharges, float totalPayableCharges) {
        this.totalPrice = totalPrice;
        this.tax = tax;
        this.deliveryCharges = deliveryCharges;
        this.totalPayableCharges = totalPayableCharges;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(float deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public float getTotalPayableCharges() {
        return totalPayableCharges;
    }

    public void setTotalPayableCharges(float totalPayableCharges) {
        this.totalPayableCharges = totalPayableCharges;
    }
}
