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

    // TODO: remove field injections
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        System.out.println("Users: " + users); // Simple logging for debugging
        return users;
    }

//    @PostMapping
//    public User createUser(@RequestBody User user) {
//        // encode the password before saving user
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
//
//    }


    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        // Check if username is taken
        boolean usernameExists = userRepository.findByUsername(user.getUsername()).isPresent();
        if (usernameExists) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Account with this username already exists."));
        }

        // Check if email is taken
        boolean emailExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if (emailExists) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Account with this email already exists."));
        }

        // If both username and email are unique, proceed with creating the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser); // Customize this response based on your requirements
    }


    // TODO: replace to separate file in dto package
    // TODO: use records
    // Inner class for error response
    private static class ErrorResponse {
        private final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        // Getter
        public String getMessage() {
            return message;
        }
    }


}
