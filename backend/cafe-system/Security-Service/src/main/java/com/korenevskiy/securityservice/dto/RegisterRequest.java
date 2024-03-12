package com.korenevskiy.securityservice.dto;

public record RegisterRequest(String username, String password, String role) {
}
