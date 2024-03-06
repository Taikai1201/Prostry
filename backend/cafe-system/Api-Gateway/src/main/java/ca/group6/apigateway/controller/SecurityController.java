package ca.group6.apigateway.controller;

import ca.group6.apigateway.dto.LoginRequest;
import ca.group6.apigateway.dto.RegistrationRequest;
import ca.group6.apigateway.model.User;
import ca.group6.apigateway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/security")
@Slf4j
@RequiredArgsConstructor
public class SecurityController {

    private final UserRepository userRepository;
    private final ReactiveAuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<Mono<String>> register(@RequestBody RegistrationRequest request) {
        log.info("Registering user with username: {}", request.username());

        if (userRepository.existsByUsername(request.username())) {
            return new ResponseEntity<>(Mono.just("Username is occupied"), HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .username(request.username())
                .password(request.password())
                .role(request.role())
                .build();

        userRepository.save(user);
        return new ResponseEntity<>(Mono.just("User created"), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Mono<List<User>>> getAllUsers() {
        return new ResponseEntity<>(Mono.just(userRepository.findAll()), HttpStatus.OK);
    }
}
