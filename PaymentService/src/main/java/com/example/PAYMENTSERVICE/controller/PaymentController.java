package com.example.PAYMENTSERVICE.controller;

import com.example.PAYMENTSERVICE.dto.PaymentRequest;
import com.example.PAYMENTSERVICE.dto.RequestVerifyPayment;
import com.example.PAYMENTSERVICE.services.RazorpayOrderCreation;
import com.razorpay.RazorpayException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private RazorpayOrderCreation razorpayOrderCreation;
    ;

    @PostMapping("/initiate")
    public String initiatePayment(@RequestBody PaymentRequest request) {
        try {
            String order = razorpayOrderCreation.createOrder(request.getAmount(), "INR", request.getOrderId());
            return order;
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/verify")
    public Boolean paymentVerify(@RequestBody RequestVerifyPayment requestVerifyPayment) {
        try {
            String data = requestVerifyPayment.getOrderId() + "|" + requestVerifyPayment.getPaymentId();
            String secret = "Zc61WSk2IElyQqC67iEZ6TlK";

            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);

            byte[] hash = mac.doFinal(data.getBytes());
            String generatedSignature = Hex.encodeHexString(hash); // ✅ Hex format

            System.out.println("Generated Signature: " + generatedSignature);
            System.out.println("Received Signature: " + requestVerifyPayment.getSignature());

            return generatedSignature.equals(requestVerifyPayment.getSignature());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
