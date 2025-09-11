package com.ecommerce.ProductService.repository;

import com.ecommerce.ProductService.entities.ProductsOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRespository extends JpaRepository<ProductsOrder,String> {
}
