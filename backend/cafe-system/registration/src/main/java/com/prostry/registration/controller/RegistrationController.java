package com.prostry.registration.controller;

import com.prostry.registration.dto.ErrorResponse;
import com.prostry.registration.model.User;
import com.prostry.registration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor

public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        System.out.println("Users: " + users); // Simple logging for debugging
        return users;
    }


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
}
