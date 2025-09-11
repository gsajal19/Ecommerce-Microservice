package com.ecommerce.ProductService.controller;

import com.ecommerce.ProductService.dto.ProductQuantity;
import com.ecommerce.ProductService.entities.ProductsOrder;
import com.ecommerce.ProductService.exception.StockNotAvailableException;
import com.ecommerce.ProductService.services.ProductInventoryService;
import com.ecommerce.ProductService.services.ProductsOrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product/order")
public class ProductsOrderController {

    @Autowired
    private ProductInventoryService productInventoryService;

    @Autowired
    private ProductsOrderService productsOrderService;

    @Transactional
    public boolean CheckOutProducts(String cartId,String orderId) {
        List<ProductQuantity> items = new ArrayList<>();// Cart API call to get Products list

//        Reduce product from inventory
        boolean response = productInventoryService.decrProductsQuantityBy(items, orderId);
        if (response) {
//          Save in Order Product table
            productsOrderService.SaveProductsWithOrderId(items, orderId);
            return true;
        } else {
            throw new StockNotAvailableException("Required Product is not available in stock anymore, please try later once product is in stock");
        }

    }






}
