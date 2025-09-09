package com.ecommerce.ProductService.controller;

import com.ecommerce.ProductService.entities.Product;
import com.ecommerce.ProductService.repository.ProductRepository;
import com.ecommerce.ProductService.services.ProductServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private  ProductServices productServices;

    public ProductController(ProductServices productServices) {
        this.productServices = productServices;
    }

    @PostMapping("/create")
    public Product createProduct(@RequestParam("name") String name,
                                 @RequestParam("price") float price,
                                 @RequestParam("quantity") int quantity,
                                 @RequestParam("image") MultipartFile image,
                                 @RequestParam("description") String description) {
        return productServices.createProduct(name, price, quantity, description, image);
    }

    @PutMapping("/update/{productId}")
    public Product updateProduct(@RequestParam("name") String name,
                                 @RequestParam("price") float price,
                                 @RequestParam("quantity") int quantity,
                                 @RequestParam("image") MultipartFile image,
                                 @RequestParam("description") String description,
                                 @PathVariable("productId") String productId) {

        return productServices.updateProduct(name,price,quantity,description,image,productId);

    }

    @GetMapping("/find/{productId}")
    public Product getProductById(@PathVariable String productId) {
        return productServices.getProductById(productId);
    }

    @GetMapping("/find/all")
    public List<Product> getAllProducts() {
        return productServices.getAllProducts();
    }

    @GetMapping("valid/{id}")
    public ResponseEntity<HashMap<String,String>> validProduct(@PathVariable String id) {
        boolean b = productServices.isProductPresent(id);
        HashMap<String,String> map = new HashMap<>();
        map.put("present",String.valueOf(b));
        return new ResponseEntity<>(map, HttpStatus.EARLY_HINTS);
    }

    @DeleteMapping("/del/{productId}")
    public boolean deleteProduct(@PathVariable String productId) {
       return productServices.deleteProduct(productId);
    }


}
