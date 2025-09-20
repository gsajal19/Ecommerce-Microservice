package com.ecommerce.ProductService.exception;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullProductValueException.class)
    public ResponseEntity<HashMap<String,String>> NullProductValueExceptionHandler(Exception e){
        HashMap<String,String> map = new HashMap<>();
        map.put("message", e.getMessage());
        map.put("success","false");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<HashMap<String,String>> ProductNotFoundExceptionHandler(Exception e){
        HashMap<String,String> map = new HashMap<>();
        map.put("message", e.getMessage());
        map.put("success","false");
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StockNotAvailableException.class)
    public ResponseEntity<HashMap<String,String>> StockNotAvailableExceptionHandler(Exception e) {
        HashMap<String,String> map = new HashMap<>();
        map.put("message", e.getMessage());
        map.put("success","false");
        return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(StockUpdateFailException.class)
    public ResponseEntity<HashMap<String,String>> StockUpdateFailExceptionHandler(Exception e) {
        HashMap<String,String> map = new HashMap<>();
        map.put("message", e.getMessage());
        map.put("success","false");
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(SomethingWentWrong.class)
    public ResponseEntity<HashMap<String,String>> SomethingWentWrongExceptionHandler(Exception e) {
        HashMap<String,String> map = new HashMap<>();
        map.put("message", e.getMessage());
        map.put("success","false");
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
