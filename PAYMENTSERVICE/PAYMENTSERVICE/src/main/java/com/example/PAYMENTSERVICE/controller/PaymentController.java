package com.example.PAYMENTSERVICE.controller;

import com.example.PAYMENTSERVICE.dto.PaymentRequest;
import com.example.PAYMENTSERVICE.services.RazorpayOrderCreation;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private RazorpayOrderCreation razorpayOrderCreation;;
    @PostMapping("/initiate")
     public String initiatePayment(@RequestBody PaymentRequest request) {
        try{
            String order = razorpayOrderCreation.createOrder(request.getAmount(),"INR",request.getOrderId());
            return order;
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }

}
