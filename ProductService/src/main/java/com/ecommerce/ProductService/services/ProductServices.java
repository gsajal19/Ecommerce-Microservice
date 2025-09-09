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

    public Product createProduct(String name,float price,int quantity,String description,MultipartFile image ) {

        if(name==null)throw new ProductNotFoundException("Product name is empty");
        if(price<=0)throw new ProductNotFoundException("Product price is empty ");
        if(quantity<=0)throw new ProductNotFoundException("Product quantity is empty");
        description = (description==null)?"":description;
        String imgURL = "defaulturl://placeholder";
        if(image!=null && !image.isEmpty()){
//        Upload in Google Drive

            imgURL = "";
        }
        String id = UUID.randomUUID().toString();
        Product product = new Product(id,name,description,price,quantity,imgURL);

        return productRepository.save(product);
    }

    public Product updateProduct(String name,float price,int quantity,String description,MultipartFile image,String productId){

        if(isProductPresent(productId)){
            Product  product = getProductById(productId);
            product.setName((name==null)?product.getName():name);
            product.setPrice((price>=0)?price:product.getPrice());
            product.setQuantity((quantity>=0)?quantity:product.getQuantity());
            product.setDescription((description==null)?product.getDescription():description);
            if(image !=null && !image.isEmpty()){
//                Replace the image from drive

//                Set image in product url
            }
            productRepository.save(product);
        }else {
            throw new ProductNotFoundException("Product not found");
        }

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
