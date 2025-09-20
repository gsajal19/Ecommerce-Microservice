package com.ecommerce.userservice.services;

import com.ecommerce.userservice.entities.User;
import com.ecommerce.userservice.exception.DataNotFoundException;
import com.ecommerce.userservice.exception.EmailDuplicationException;
import com.ecommerce.userservice.exception.SomethingWentWrong;
import com.ecommerce.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User generateUser(User user) {
        try {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            if (user.getPassword() != null) {
                String hashPasswd = bCryptPasswordEncoder.encode(user.getPassword());
                user.setPassword(hashPasswd);
            }
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new EmailDuplicationException("Email already exists");
            }
            userRepository.save(user);

            return getUserById(user.getId());

        }catch(Exception e){
            throw new SomethingWentWrong("Unexpected error occurred");
        }

    }

    public User getUserById(String id){
        return userRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Required Data with id: "+id+" not found"));
    }

    public List<User> getAllUser(){

        return userRepository.findAll();
    }

    @Transactional
    public boolean deleteUserById(String id){
        try {
            userRepository.deleteById(id);
            return userRepository.existsById(id);
        }catch (Exception e){
            throw new SomethingWentWrong("Unexpected error occurred");
        }
    }
    public User getUserByEmail(String email){
        User u = new User();
        u.setEmail(email);
        return userRepository.findByEmail(email).orElse(u);
    }
    public boolean validateUserPasswd(String password,String id){
        return bCryptPasswordEncoder.matches(password,getUserById(id).getPassword());
    }

    public ResponseEntity<HashMap<String, String>> validCredentials(String username, String password) {
        HashMap<String,String> response = new HashMap();
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if(!optionalUser.isPresent()){
            response.put("error","user not found");
            response.put("status", "false");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        User user = optionalUser.get();

        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            response.put("error","password not match");
            response.put("status", "false");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        response.put("status", "true");
        response.put("userId", user.getId());
        response.put("role", "USER,ADMIN");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
