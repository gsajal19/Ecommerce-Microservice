package com.ecommerce.userservice.services;

import com.ecommerce.userservice.entities.User;
import com.ecommerce.userservice.exception.DataNotFoundException;
import com.ecommerce.userservice.exception.EmailDuplicationException;
import com.ecommerce.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;


    private BCryptPasswordEncoder bCryptPasswordEncoder;

    UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public User generateUser(User user) {
        String id = UUID.randomUUID().toString();
        user.setId(id);
        if(user.getPassword() != null) {
            String hashPasswd = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(hashPasswd);
        }
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new EmailDuplicationException("Email already exists");
        }
        userRepository.save(user);
        User user1 = getUserById(user.getId());

        return user1;

    }

    public User getUserById(String id){
        User user =userRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Required Data with id: "+id+" not found"));
        return user;
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public boolean deleteUserById(String id){
        userRepository.deleteById(id);
        return userRepository.existsById(id);
    }
    public User getUserByEmail(String email){
        User u = new User();
        u.setEmail(email);
        return userRepository.findByEmail(email).orElse(u);
    }
    public boolean validateUserPasswd(String password,String id){
        return bCryptPasswordEncoder.matches(password,getUserById(id).getPassword());
    }
}
