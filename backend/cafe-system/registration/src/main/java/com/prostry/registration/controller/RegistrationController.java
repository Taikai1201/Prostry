package com.prostry.registration.controller;

import com.prostry.registration.model.User;
import com.prostry.registration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration")

public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> getAllUsers() {
//        return userRepository.findAll();
        List<User> users = userRepository.findAll();
        System.out.println("Users: " + users); // Simple logging for debugging
        return users;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        // encode the password before saving user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


}
