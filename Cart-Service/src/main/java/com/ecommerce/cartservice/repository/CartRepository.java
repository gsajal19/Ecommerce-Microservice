package com.ecommerce.cartservice.repository;

import com.ecommerce.cartservice.entities.UserCartDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<UserCartDetails,String> {
     Optional<UserCartDetails> findByUserId(String userId);
}
