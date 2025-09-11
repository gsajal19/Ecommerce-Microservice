package com.ecommerce.cartservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity()
public class UserCartDetails {

    private String userId;
    @Id
    private String cartId;

    public UserCartDetails() {
    }

    public UserCartDetails(String userId, String cartId) {
        this.userId = userId;
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }
}
