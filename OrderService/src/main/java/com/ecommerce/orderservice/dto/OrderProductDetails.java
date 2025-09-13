package com.ecommerce.orderservice.dto;

public class OrderProductDetails {

private String productId;
private int quantity;
private ProductDetails productDetails;

    public OrderProductDetails() {
    }

    public OrderProductDetails(String productId, int quantity, ProductDetails productDetails) {
        this.productId = productId;
        this.quantity = quantity;
        this.productDetails = productDetails;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }
}
