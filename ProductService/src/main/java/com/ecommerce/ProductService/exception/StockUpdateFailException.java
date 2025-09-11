package com.ecommerce.ProductService.exception;

public class StockUpdateFailException extends RuntimeException{
    public StockUpdateFailException(String message){
        super(message);
    }
}
