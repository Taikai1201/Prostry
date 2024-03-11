package com.prostry.registration.controller;

import com.prostry.registration.config.JwtUtil;
import com.prostry.registration.dto.AuthenticationResponse;
import com.prostry.registration.dto.ErrorResponse;
import com.prostry.registration.dto.LoginRequest;
import com.prostry.registration.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.prostry.registration.repository.UserRepository;


@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    // TODO: add RequiredConstructor annotation to remove Field injections - FINISHED
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        final String jwt = jwtUtil.generateToken(authenticationRequest.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }

    // TODO: move to separate file in dto package - FINISHED


    // handle error

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(e.getMessage()));
    }

    // TODO: move to separate file in dto package - FINISHED



    // TODO: remove this unnecessary declaration (you have the same class in separate file) - FINISHED


}
