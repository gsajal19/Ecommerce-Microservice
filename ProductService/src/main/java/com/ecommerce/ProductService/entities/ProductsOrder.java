package com.ecommerce.ProductService.entities;

import jakarta.persistence.*;

@Entity(name = "productsOrder")
public class ProductsOrder {
    enum OrderStatus {
        DEDUCT,
        ROLLBACK,

    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String productId;
    private String OrderId;

    private int quantity;
    private float price;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus action = OrderStatus.DEDUCT;

    public ProductsOrder() {

    }

    public ProductsOrder(int id, String productId, String orderId, int quantity, float price) {
        this.id = id;
        this.productId = productId;
        OrderId = orderId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public OrderStatus getAction() {
        return action;
    }

    public void setAction(OrderStatus action) {
        this.action = action;
    }
}
