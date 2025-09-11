package com.ecommerce.ProductService.dto;

import java.util.List;

public class OrderProductQuantity {

    private String orderId;
    private List<ProductQuantity> items;

    public OrderProductQuantity(String orderId, List<ProductQuantity> items) {
        this.orderId = orderId;
        this.items = items;
    }

    public OrderProductQuantity() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<ProductQuantity> getItems() {
        return items;
    }

    public void setItems(List<ProductQuantity> items) {
        this.items = items;
    }
}
