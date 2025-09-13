package com.ecommerce.ProductService.services;

import com.ecommerce.ProductService.dto.ProductQuantity;
import com.ecommerce.ProductService.entities.Product;
import com.ecommerce.ProductService.entities.ProductsOrder;
import com.ecommerce.ProductService.exception.StockNotAvailableException;
import com.ecommerce.ProductService.repository.ProductOrderRespository;
import com.ecommerce.ProductService.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInventoryService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOrderRespository productOrderRespository;
    @Autowired
    private ProductServices productServices;


    public boolean isProductInStock(String productId) {
        Product product = productServices.getProductById(productId);
        return product.getQuantity() > 0;
    }


    public boolean isProductAvailableForUser(String productId, int userQuantity) {
        Product product = productServices.getProductById(productId);

        if (product.getQuantity() <= 0) {
            return false;
        }

        return product.getQuantity() >= userQuantity;
    }

    @Transactional
    public boolean incrProductsQuantityBy(List<ProductQuantity> items,String orderId) {

        try {
            for (ProductQuantity product : items) {
                Product productDB = productServices.getProductById(product.getProductId());
                productDB.setQuantity(productDB.getQuantity() + product.getQuantity());
                productRepository.save(productDB);
            }

        }catch(Exception e){
            throw new StockNotAvailableException("Failed to Increment the product quantity");
        }
        return true;
    }

    @Transactional
    public boolean decrProductsQuantityBy(List<ProductQuantity> items, String orderId) {


            for (ProductQuantity product : items) {
                Product productDB = productServices.getProductById(product.getProductId());
                if(productDB.getQuantity()-product.getQuantity()<0) {
                        throw new StockNotAvailableException("Product is Out of Stock");
                }
                productDB.setQuantity(productDB.getQuantity() - product.getQuantity());
//                productOrderRespository.save(new ProductsOrder(product.getProductId(),orderId,product.getQuantity(),productDB.getPrice()));
                productRepository.save(productDB);

            }

        return true;
    }

    public float getProductPrice(String productId){
        Product product = productServices.getProductById(productId);
        return product.getPrice();
    }

}
