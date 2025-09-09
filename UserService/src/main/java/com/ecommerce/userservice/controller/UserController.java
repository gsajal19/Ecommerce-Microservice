package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.entities.User;
import com.ecommerce.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public User createUser(@RequestBody  User user){
       return userService.generateUser(user);
    }
    @GetMapping("/{id}")
    public User findUserById(@PathVariable String id){
        return userService.getUserById(id);
    }
    @GetMapping("/all")
    public List<User> findAllUser(){
        return userService.getAllUser();
    }
    @DeleteMapping("/del/{id}")
    public ResponseEntity<HashMap<String,Object>> deleteUser(@PathVariable String id){
        boolean b=  userService.deleteUserById(id);
        HashMap<String,Object> map = new HashMap<>();
        if(b){
           map.put("status","success");
           map.put("message","User successfully deleted!!");

        }
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }
    @GetMapping("/valid/{id}")
    public boolean validUSer(@PathVariable String id){
        User user = userService.getUserById(id);
        return user.getId().equals(id);
    }
    @GetMapping("/mail")
    public User findByEmail(@RequestParam String email){
        return userService.getUserByEmail(email);
    }

    @GetMapping("/valid/{id}/ps/{passwd}")
    public ResponseEntity<Boolean> validatePassword(@PathVariable String passwd, @PathVariable String id){
        return new ResponseEntity<>(userService.validateUserPasswd(passwd,id),HttpStatus.ACCEPTED);

    }

}
