package com.ecommerce.ProductService.controller;

import com.ecommerce.ProductService.dto.AllProductDetails;
import com.ecommerce.ProductService.dto.ProductOrderRequest;
import com.ecommerce.ProductService.dto.ProductQuantity;
import com.ecommerce.ProductService.exception.StockNotAvailableException;
import com.ecommerce.ProductService.services.ProductInventoryService;
import com.ecommerce.ProductService.services.ProductsOrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products/order")
public class ProductsOrderController {

    @Autowired
    private ProductInventoryService productInventoryService;

    @Autowired
    private ProductsOrderService productsOrderService;

    @PostMapping("/check-out-products")
    @Transactional
    public boolean CheckOutProducts(@RequestBody ProductOrderRequest request) {

        List<AllProductDetails> data = request.getProductDetails();
        String orderId = request.getOrderId();
        List<ProductQuantity> items = new ArrayList<>();
        for(AllProductDetails item : data) {
            items.add(new ProductQuantity(item.getProductId(), item.getQuantity()));
        }

//        Reduce product from inventory
        boolean response = productInventoryService.decrProductsQuantityBy(items, orderId);
//        Save product with orderID in DB
        if (response) {
//          Save in Order Product table
            productsOrderService.SaveProductsWithOrderId(items, orderId);
            return true;
        } else {
            throw new StockNotAvailableException("Required Product is not available in stock anymore, please try later once product is in stock");
        }

    }






}
