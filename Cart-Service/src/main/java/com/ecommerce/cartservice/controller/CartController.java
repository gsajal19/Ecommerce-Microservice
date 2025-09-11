package com.ecommerce.cartservice.controller;

import com.ecommerce.cartservice.dto.ProductQuantity;
import com.ecommerce.cartservice.entities.CartProductQuantity;
import com.ecommerce.cartservice.sevices.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/add-to-cart/{productId}")
    public ResponseEntity<HashMap<String,String>> addToCart(@PathVariable String productId, @RequestParam int quantity, @RequestParam String userid) {
        String cartid = getUserCartId(userid);
        boolean b= cartService.addProductToCart(productId, quantity, cartid);
        HashMap<String,String> map = new HashMap<>();
        map.put("status","success");
        map.put("Mesage","Successfully added");
        return new ResponseEntity<>(map, HttpStatusCode.valueOf(200));
    }

    @GetMapping("remove-from-cart/{productId}")
    public ResponseEntity<HashMap<String,String>> removeFromCart(@PathVariable String productId, @RequestParam String userid) {
        String cartid = getUserCartId(userid);
        boolean b=cartService.removeProductFromCart(productId,cartid);
        HashMap<String,String> map = new HashMap<>();
        map.put("status",String.valueOf(true));
        map.put("Message","Successfully removed");
        return new ResponseEntity<>(map, HttpStatusCode.valueOf(200));
    }

    @GetMapping("remove/quantity/{productid}")
    public CartProductQuantity reduceProductQuantity(@PathVariable String productid, @RequestParam String userid) {
        String cartid = getUserCartId(userid);
        CartProductQuantity b= cartService.decrProductQuantity(productid,cartid);
        return b;

    }

    @GetMapping("add/quantity/{productid}")
    public CartProductQuantity addProductQuantity(@PathVariable String productid, @RequestParam String userid) {
        String cartid = getUserCartId(userid);
        CartProductQuantity b= cartService.incrProductQuantity(productid,cartid);
        return b;
    }

    @GetMapping("/find")
    public String getUserCartId(@RequestParam String userid){
        return cartService.generateIfNotExistsCartId(userid);
    }

//    Return Latest Cart Everytime
    @GetMapping("/all")
    public List<CartProductQuantity> getAllCartProduct(@RequestParam String userid){

        return cartService.getAllProductsFromCartId(getUserCartId(userid));
    }

    @DeleteMapping("/remove-all")
    public boolean removeAllCartProduct(@RequestParam String userid){

           return cartService.removeAllProductsFromCartId(getUserCartId(userid));
    }



}
