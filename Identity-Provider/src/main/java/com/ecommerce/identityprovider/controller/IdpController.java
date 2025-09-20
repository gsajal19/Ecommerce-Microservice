package com.ecommerce.identityprovider.controller;

import com.ecommerce.identityprovider.dao.ReqLoginCredential;
import com.ecommerce.identityprovider.dao.ReqUserRegistration;
import com.ecommerce.identityprovider.service.IdpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class IdpController {

    @Autowired
    private IdpService idpService;

    @PostMapping("/login")
    public ResponseEntity<HashMap<String,String>> login(@RequestBody ReqLoginCredential reqLoginCredential) {
        return idpService.login(reqLoginCredential.getUsername(),reqLoginCredential.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<HashMap<String,String>> register(@RequestBody ReqUserRegistration reqUserRegistration){
        return idpService.register(reqUserRegistration);
    }

    @GetMapping("/{token}")
    public ResponseEntity<HashMap<String,String>> getToken(@PathVariable String token){
        return idpService.validateToken(token);
    }


}
