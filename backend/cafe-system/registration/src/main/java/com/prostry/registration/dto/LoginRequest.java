package com.prostry.registration.dto;


// TODO: add Lombok annotations to replace explicit setters/getters - FINISHED
// TODO: Additionally: make DTOs Java records so we don't have to worry about data access

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;


}
