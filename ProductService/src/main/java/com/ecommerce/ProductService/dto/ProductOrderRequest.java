package com.ecommerce.ProductService.dto;

import java.util.List;

public class ProductOrderRequest {
    private List<AllProductDetails> productDetails;
    private String orderId;

    public List<AllProductDetails> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<AllProductDetails> productDetails) {
        this.productDetails = productDetails;
    }

    @Override
    public String toString() {
        return "ProductOrderRequest{" +
                "productDetails=" + productDetails +
                ", orderId='" + orderId + '\'' +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
// Getters and Setters
}
