package com.ecommerce.orderservice.dto;

import java.util.List;

public class RequestPlaceOrder {
    private List<ProductDetails> productDetails;
    private String OrderId;

    public RequestPlaceOrder(List<ProductDetails> productDetails, String orderId) {
        this.productDetails = productDetails;
        OrderId = orderId;
    }

    public List<ProductDetails> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetails> productDetails) {
        this.productDetails = productDetails;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }
}
