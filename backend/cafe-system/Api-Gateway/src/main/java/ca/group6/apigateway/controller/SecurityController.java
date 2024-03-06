package ca.group6.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @GetMapping("/login")
    public Mono<String> login() {
        return Mono.just("Hello login");
    }
}
