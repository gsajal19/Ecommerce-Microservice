package com.ecommerce.ProductService.exception;

public class StockNotAvailableException extends RuntimeException {

    public StockNotAvailableException(String message) {
        super(message);
    }

}
