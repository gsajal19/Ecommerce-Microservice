package com.ecommerce.userservice.repository;

import com.ecommerce.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    public boolean existsByEmail(String email);
}
