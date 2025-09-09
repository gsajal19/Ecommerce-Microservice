package com.ecommerce.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailDuplicationException.class)
    public ResponseEntity<HashMap<String,Object>> EmailExceptionHandler(Exception e){
        HashMap<String,Object> map = new HashMap<>();
        map.put("message",e.getMessage());
        map.put("status", "failed");
        return new ResponseEntity<>(map, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<HashMap<String,Object>> DataNotFoundExceptionHandler(Exception e){
        HashMap<String,Object> map = new HashMap<>();
        map.put("message",e.getMessage());
        map.put("status", "failed");
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
}
