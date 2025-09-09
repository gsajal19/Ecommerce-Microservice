package com.ecommerce.ProductService.repository;

import com.ecommerce.ProductService.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
