package com.ecommerce.ProductService.controller;

import com.ecommerce.ProductService.entities.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    public Product createProduct(Product product) {
        return null;
    }

    public Product updateProduct(Product product) {
        return null;
    }

    public Product getProductById(int id) {
        return null;
    }
    public List<Product> getAllProducts() {
        return null;
    }
    public void deleteProduct(int id) {

    }


}
