package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.OrderProductDetails;
import com.ecommerce.orderservice.dto.ViewOrderListCatalog;
import com.ecommerce.orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/generate-order")
    public ViewOrderListCatalog ViewOrderCatalog(@RequestParam String userid){
        return orderService.generateOrder(userid);

    }

    @GetMapping("/place-order")
    public ResponseEntity<?> orderPlace(@RequestParam String userid){
        return new ResponseEntity<>(orderService.placeOrder(userid),HttpStatusCode.valueOf(200));
//        return new ResponseEntity<>(true, HttpStatusCode.valueOf(200));

    }
}
