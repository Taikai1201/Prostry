package com.prostry.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.prostry.registration.repository.UserRepository;


@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @PostMapping
//    public ResponseEntity<?> login(@RequestBody) {
//        return ResponseEntity.ok("Login successful");
//    }

}
