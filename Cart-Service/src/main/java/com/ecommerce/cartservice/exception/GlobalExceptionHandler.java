package com.ecommerce.cartservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductQuantityOutOfBoundException.class)
    public ResponseEntity<HashMap<String,String>> ProductQuantityOutOfBoundExceptionHandler(Exception e){
        HashMap<String,String> map = new HashMap<>();
        map.put("message",e.getMessage());
        map.put("success","false");
        return new ResponseEntity<>(map, HttpStatusCode.valueOf(200));
    }

    @ExceptionHandler(ProductReAddedException.class)
    public ResponseEntity<HashMap<String,String>> ProductReAddedExceptionHandler(Exception e){
        HashMap<String,String> map = new HashMap<>();
        map.put("message",e.getMessage());
        map.put("success","true");
        return new ResponseEntity<>(map, HttpStatusCode.valueOf(200));
    }

    @ExceptionHandler(ProductNotFoundCartException.class)
    public ResponseEntity<HashMap<String,String>> ProductNotFoundExceptionHandler(Exception e){
        HashMap<String,String> map = new HashMap<>();
        map.put("message",e.getMessage());
        map.put("success","false");
        return new ResponseEntity<>(map, HttpStatusCode.valueOf(200));
    }

    @ExceptionHandler(ProductOutOfStockException.class)
    public ResponseEntity<HashMap<String,String>> ProductOutOfStockExceptionHandler(Exception e){
        HashMap<String,String> map = new HashMap<>();
        map.put("message",e.getMessage());
        map.put("success","false");
        return new ResponseEntity<>(map, HttpStatusCode.valueOf(200));
    }
}
