package com.ecommerce.orderservice.services;

import com.ecommerce.orderservice.dto.*;
import com.ecommerce.orderservice.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    private final float taxRate = 0.18f;
    private final float deliveryCharges = 50;

    public ViewOrderListCatalog generateOrder(String userId) {
        float totalProductsPrice = 0;
        float tax = 0;

        OrderProductDetails[] items = restTemplate.getForObject("http://localhost:8083/cart/all?userid=" + userId, OrderProductDetails[].class);
        List<ProductDetails> products = new ArrayList<>();

        for (OrderProductDetails item : items) {
            totalProductsPrice += item.getProductDetails().getPrice();
            products.add(new ProductDetails(
                    item.getProductId(),
                    item.getProductDetails().getName(),
                    item.getQuantity(),
                    item.getProductDetails().getPrice()
            ));
        }

        tax = (totalProductsPrice * taxRate);
        float delivery = (totalProductsPrice >= 500) ? 0 : deliveryCharges;
        TotalCharges totalCharges = new TotalCharges(totalProductsPrice, tax, delivery, totalProductsPrice + tax + delivery);

        return new ViewOrderListCatalog(products, totalCharges);
    }

    public String placeOrder(String userId) {
        ViewOrderListCatalog viewOrderListCatalog = generateOrder(userId);
        String orderId = UUID.randomUUID().toString();

        List<ProductDetails> products = viewOrderListCatalog.getProducts();
        RequestPlaceOrder request = new RequestPlaceOrder(products, orderId);

        try {
            // Product service call
            boolean productCheck = Boolean.TRUE.equals(
                    restTemplate.postForObject("http://localhost:8082/products/order/check-out-products", request, Boolean.class)
            );

            if (!productCheck) {
                return "Product Service rejected the order due to product issues.";
            }

            // Proceed to payment
            System.out.println("Waiting for payment");
            double amount = viewOrderListCatalog.getTotalCharges().getTotalPayableCharges();

            return restTemplate.postForObject(
                    "http://localhost:8085/payment/initiate",
                    new PaymentRequest(amount, orderId, userId),
                    String.class
            );

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Read and parse error response from Product Service
            String errorResponse = ex.getResponseBodyAsString();
            try {
                ObjectMapper mapper = new ObjectMapper();
                ErrorResponse parsedError = mapper.readValue(errorResponse, ErrorResponse.class);
                return "Error from Product Service: " + parsedError.getMessage();
            } catch (Exception parsingEx) {
                return "Error from Product Service (unparsed): " + errorResponse;
            }

        } catch (Exception e) {
            // Catch unexpected exceptions
            return "Unexpected error in placing order: " + e.getMessage();
        }
    }
}
