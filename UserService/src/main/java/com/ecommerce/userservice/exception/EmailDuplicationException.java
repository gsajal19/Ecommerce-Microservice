package com.ecommerce.userservice.exception;

public class EmailDuplicationException extends RuntimeException{
    public EmailDuplicationException(String message) {
        super(message);
    }
}
