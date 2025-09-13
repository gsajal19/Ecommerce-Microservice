package com.example.PAYMENTSERVICE.services;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayOrderCreation {

    @Value("${razorpay.key_id}")
    private String razorpayKeyId;

    @Value("${razorpay.key_secret}")
    private String razorpayKeySecret;

    public String createOrder(double amount, String currency, String receiptId) throws RazorpayException {
        RazorpayClient client = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

        JSONObject options = new JSONObject();
        options.put("amount", (int) (amount * 100)); // Razorpay expects amount in paise
        options.put("currency", currency);
        options.put("receipt", receiptId);
        options.put("payment_capture", 1);

        Order order = client.orders.create(options);

        return order.toString(); // or order.get("id") to just return orderId
    }
}
