package com.ecommerce.cartservice.repository;

import com.ecommerce.cartservice.entities.CartProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartProductQuantityRepository extends JpaRepository<CartProductQuantity, String> {
    List<CartProductQuantity> findByCartId(String cartId);
    void deleteByCartId(String cartId);
    Optional<CartProductQuantity> findByCartIdAndProductId(String cartId, String productId);
    void deleteByCartIdAndProductId(String cartId, String productId);
}
