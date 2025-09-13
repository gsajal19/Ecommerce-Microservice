package com.ecommerce.orderservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class OrderPlaced {
    @Id
    private String orderId;
    private float totalAmount;
    private int totalItem;
    private String userId;
    private Status status = Status.PENDING;
}

enum Status{
        FAILED,
        PENDING,
        COMPLETED
        }
