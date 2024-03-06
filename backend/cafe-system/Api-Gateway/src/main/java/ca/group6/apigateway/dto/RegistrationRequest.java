package ca.group6.apigateway.dto;

public record RegistrationRequest(
        String username, String password, String role) {
}
