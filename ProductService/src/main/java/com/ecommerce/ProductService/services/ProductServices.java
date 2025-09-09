package com.ecommerce.ProductService.services;

import com.ecommerce.ProductService.entities.Product;
import com.ecommerce.ProductService.exception.ProductNotFoundException;
import com.ecommerce.ProductService.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServices {

    private ProductRepository productRepository;

    public ProductServices(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(@RequestParam("name") String name,
                                 @RequestParam("price") float price,
                                 @RequestParam("quantity") int quantity,
                                 @RequestParam('image') MultipartFile image,
                                 @RequestParam("description") String description ) {

        if(name==null)throw new ProductNotFoundException("Product name is empty");
        if(price==0)throw new ProductNotFoundException("Product price is empty");
        if(quantity==0)throw new ProductNotFoundException("Product quantity is empty");

//        Upload in Google Drive
        if(image!=null || !image.isEmpty()){

        }
        return productRepository.save(product);
    }
    public Product getProductById(String id) {
        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product is not available"));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public boolean isProductPresent(String id) {
        return productRepository.findById(id).isPresent();
    }

    public boolean deleteProduct(String id) {
         productRepository.deleteById(id);
         return isProductPresent(id);
    }
}
