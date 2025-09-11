package com.ecommerce.cartservice.entities;

import com.ecommerce.cartservice.dto.ProductDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity(name = "cartProducts")
@Table(
        name = "cartProducts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"cartId", "productId"})
        }
)
public class CartProductQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int sequence;
    private String productId;

    private int quantity;
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String cartId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private ProductDetails productDetails;
//    @Transient
//    private boolean availability;

    public CartProductQuantity(String productId, int quantity, String cartId) {
        this.productId = productId;
        this.quantity = quantity;
        this.cartId = cartId;
    }

    public CartProductQuantity() {
    }

    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    //    public boolean isAvailability() {
//        return availability;
//    }
//
//    public void setAvailability(boolean availability) {
//        this.availability = availability;
//    }
}
