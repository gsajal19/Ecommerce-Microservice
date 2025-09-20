package com.ecommerce.ProductService.controller;

import com.ecommerce.ProductService.dto.OrderProductQuantity;
import com.ecommerce.ProductService.dto.ProductQuantity;
import com.ecommerce.ProductService.entities.Product;
import com.ecommerce.ProductService.services.ProductInventoryService;
import com.ecommerce.ProductService.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/inventory")
public class UpdateProductInventory {

    @Autowired
    private ProductInventoryService productInventoryService;



    @GetMapping("/check/cart/{productId}/{customerRequirement}")
    public boolean productAvailabilityForUser(@PathVariable("productId") String productId, @PathVariable("customerRequirement") int customerRequirement) {
        return productInventoryService.isProductAvailableForUser(productId,customerRequirement);
    }

    @GetMapping("/check/stock/{productId}")
    public boolean productAvailability(@PathVariable("productId") String productId){
        return productInventoryService.isProductInStock(productId);
    }

    @PostMapping("/incr")
    public boolean insertProductQuantityToInventory(@RequestBody OrderProductQuantity orderProductQuantity) {
        return productInventoryService.incrProductsQuantityBy(orderProductQuantity.getItems(),orderProductQuantity.getOrderId());
    }

    @PostMapping("/decr")
    public boolean deductProductQuantityFromInventory(@RequestBody OrderProductQuantity orderProductQuantity) {
        return productInventoryService.decrProductsQuantityBy(orderProductQuantity.getItems(),orderProductQuantity.getOrderId());
    }



}
