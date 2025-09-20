package com.ecommerce.identityprovider.service;

import com.ecommerce.identityprovider.dao.ReqLoginCredential;
import com.ecommerce.identityprovider.dao.ReqUserRegistration;
import com.ecommerce.identityprovider.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.HashSet;

@Service
public class IdpService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RestTemplate restTemplate;

    private HashSet blackListed = new HashSet();

    public ResponseEntity<HashMap<String, String>> login(String username, String password) {
        HashMap<String, String> response = new HashMap<>();
        try {

            ResponseEntity<HashMap> userServiceResponse =
                    restTemplate.postForEntity(
                            "http://USER-SERVICE/users/valid-credential",
                            new ReqLoginCredential(username, password),
                            HashMap.class
                    );

            HashMap<String, Object> userResponse = userServiceResponse.getBody();

            if (userResponse != null && "true".equalsIgnoreCase(String.valueOf(userResponse.get("status")))) {

                String userId = String.valueOf(userResponse.get("userId"));
                String[] role = String.valueOf(userResponse.get("role")).split(",");
                String token = jwtUtils.generateToken(userId,role);

                response.put("token", token);
                response.put("message", "Login successful");


                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Invalid username or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

        } catch (Exception e) {
            response.put("error", "Unable to process login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<HashMap<String, String>> register(ReqUserRegistration reqUserRegistration) {


        ReqUserRegistration user =restTemplate.postForObject("http://USER-SERVICE/users/create", reqUserRegistration, ReqUserRegistration.class);
        if(user!=null){
            HashMap<String, String> response = new HashMap<>();
            response.put("message", "Registration successful");
            response.put("status", "true");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<HashMap<String, String>> validateToken(String token) {
        String userId = jwtUtils.validateTokenAndGetUserId(token);
        HashMap<String,String> response = new HashMap<>();
        if(userId==null){
            response.put("error", "Invalid token");
            response.put("status", "false");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        if(blackListed.contains(userId)){
            response.put("error", "User Black Listed");
            response.put("status", "false");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        response.put("userId",userId );
        response.put("message", "Valid token");
        response.put("status", "true");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
