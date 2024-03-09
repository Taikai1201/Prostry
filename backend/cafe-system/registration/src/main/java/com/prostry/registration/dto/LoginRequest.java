package com.prostry.registration.dto;


// TODO: add Lombok annotations to replace explicit setters/getters
// TODO: Additionally: make DTOs Java records so we don't have to worry about data access
public class LoginRequest {

    private String username;
    private String password;

    // getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
