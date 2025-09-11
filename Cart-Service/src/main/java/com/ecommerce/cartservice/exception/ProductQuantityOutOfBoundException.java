package com.ecommerce.cartservice.exception;

public class ProductQuantityOutOfBoundException extends RuntimeException {
    public ProductQuantityOutOfBoundException(String message) {
        super(message);
    }
}
