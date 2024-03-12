package com.korenevskiy.securityservice.controller;

import com.korenevskiy.securityservice.dto.LoginRequest;
import com.korenevskiy.securityservice.dto.RegisterRequest;
import com.korenevskiy.securityservice.model.User;
import com.korenevskiy.securityservice.repository.UserRepository;
import com.korenevskiy.securityservice.service.JwtCore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/security")
@Slf4j
@RequiredArgsConstructor
public class SecurityController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtCore jwtCore;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        log.info("Registering new user {}", request);

        if (userRepository.existsByUsername(request.username())) {
            return ResponseEntity.badRequest().body("Username is occupied");
        }

        User newUser = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();

        newUser = userRepository.save(newUser);
        return ResponseEntity.ok("User created. ID: " + newUser.getId());
    }

    @PostMapping("/authorize")
    public String getToken(@RequestBody LoginRequest request) {

        Authentication authenticationRequest = UsernamePasswordAuthenticationToken
                .unauthenticated(request.username(), request.password());

        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

        log.info("Generating token for user: {}", authenticationResponse.getName());
        User user = (User) authenticationResponse.getPrincipal();
        return jwtCore.generateToken(user);
    }
}
