package com.korenevskiy.securityservice.controller;

import com.korenevskiy.securityservice.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/secured")
public class SecuredController {

    @GetMapping("/user")
    public String allUsers(Authentication authentication) {
        String username = authentication.getName();
        String commaSeparatedListOfAuthorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return String.format("User page. Username: %s | Authorities: %s", username, commaSeparatedListOfAuthorities);
    }

    @GetMapping("/admin")
    public String admin(Authentication authentication) {
        String username = authentication.getName();
        String commaSeparatedListOfAuthorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return String.format("Admin Page. Username: %s | Authorities: %s", username, commaSeparatedListOfAuthorities);
    }

}
