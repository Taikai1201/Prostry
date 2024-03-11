package com.prostry.registration.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        // Check the type of authException and customize the message accordingly
        String message;
        if (authException instanceof UsernameNotFoundException) {
            message = "{\"error\": \"User not found with the provided username.\"}";
        } else if (authException instanceof BadCredentialsException) {
            message = "{\"error\": \"Incorrect password.\"}";
        } else {
            message = "{\"error\": \"Authentication failed, incorrect Username or Password.\"}";
        }

        response.getWriter().write(message);
    }
}
