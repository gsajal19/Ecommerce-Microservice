package com.ecommerce.cartservice.exception;

public class ProductNotFoundCartException extends RuntimeException{
    public ProductNotFoundCartException(String message) {
        super(message);
    }
}
