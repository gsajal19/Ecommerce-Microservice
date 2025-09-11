package com.ecommerce.cartservice.sevices;

import com.ecommerce.cartservice.dto.ProductDetails;
import com.ecommerce.cartservice.dto.ProductQuantity;
import com.ecommerce.cartservice.entities.CartProductQuantity;
import com.ecommerce.cartservice.entities.UserCartDetails;
import com.ecommerce.cartservice.exception.ProductNotFoundCartException;
import com.ecommerce.cartservice.exception.ProductQuantityOutOfBoundException;
import com.ecommerce.cartservice.exception.ProductReAddedException;
import com.ecommerce.cartservice.repository.CartProductQuantityRepository;
import com.ecommerce.cartservice.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CartProductQuantityRepository cartProductQuantityRepository;

    public String generateIfNotExistsCartId(String userid){
//        First Find from redis and return
        UserCartDetails cartDetails = cartRepository.findByUserId(userid).orElseGet(()->{
            UserCartDetails userCartDetails = new UserCartDetails(userid,UUID.randomUUID().toString());
            cartRepository.save(userCartDetails);
            return userCartDetails;
        });
//        store in redis
        return cartDetails.getCartId();
    }

    public boolean addProductToCart(String productId, int quantity, String cartId){


            boolean response = Boolean.TRUE.equals(restTemplate.getForObject("http://localhost:8082/products/inventory/check/stock/" + productId, Boolean.class));
            if(response) {
                if (!cartProductQuantityRepository.findByCartIdAndProductId(cartId, productId).isPresent()) {
                    cartProductQuantityRepository.save(new CartProductQuantity(productId, quantity, cartId));
                } else {
                    incrProductQuantity(productId, cartId);
                    throw new ProductReAddedException("product is already present and increased by quantity");
                }
            }else{
                throw new ProductNotFoundCartException("product is not in Stock");
            }

            return cartProductQuantityRepository.findByCartIdAndProductId(cartId,productId).isPresent();
    }


    public List<CartProductQuantity> getAllProductsFromCartId(String cartId){

        List<CartProductQuantity> cartProductQuantities = cartProductQuantityRepository.findByCartId(cartId);
        for(CartProductQuantity cartProductQuantity : cartProductQuantities){
            ProductDetails productDetails = restTemplate.getForObject("http://localhost:8082/products/find/"+cartProductQuantity.getProductId(),ProductDetails.class);
            cartProductQuantity.setProductDetails(productDetails);
        }
        return cartProductQuantities;
    }

    public CartProductQuantity incrProductQuantity(String product, String cartId){
        CartProductQuantity cartProductQuantity = cartProductQuantityRepository.findByCartIdAndProductId(cartId,product).orElse(null);
        if(cartProductQuantity != null){
            cartProductQuantity.setQuantity(cartProductQuantity.getQuantity()+1);
            cartProductQuantityRepository.save(cartProductQuantity);
            return cartProductQuantity;
        }
        return null;
    }
    public CartProductQuantity decrProductQuantity(String product, String cartId){
        CartProductQuantity cartProductQuantity = cartProductQuantityRepository.findByCartIdAndProductId(cartId,product).orElse(null);
        if(cartProductQuantity != null){
            if(cartProductQuantity.getQuantity() <= 1){
               removeProductFromCart(product,cartId);
               throw new ProductQuantityOutOfBoundException("Product has been removed successfully");
            }else{
                cartProductQuantity.setQuantity(cartProductQuantity.getQuantity()-1);
                cartProductQuantityRepository.save(cartProductQuantity);
            }

            return cartProductQuantity;
        }
        return null;
    }
    @Transactional
    public boolean removeProductFromCart(String productId, String cartId){
        boolean b = cartProductQuantityRepository.findByCartIdAndProductId(cartId,productId).isPresent();
        if(!b){
            throw new ProductNotFoundCartException("Product is not available in cart");
        }
        cartProductQuantityRepository.deleteByCartIdAndProductId(cartId, productId);
        return cartProductQuantityRepository.findByCartIdAndProductId(cartId, productId).isPresent();
    }

    @Transactional
    public boolean removeAllProductsFromCartId(String cartId){
        cartProductQuantityRepository.deleteByCartId(cartId);
        return cartProductQuantityRepository.findByCartId(cartId).isEmpty();
    }


}
