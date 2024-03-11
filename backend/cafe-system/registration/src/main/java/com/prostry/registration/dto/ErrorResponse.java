package com.prostry.registration.dto;

//public class ErrorResponse {
//    private final String error;
//
//    public ErrorResponse(String error) {
//        this.error = error;
//    }
//
//    public String getError() {
//        return error;
//    }
//}

public record ErrorResponse(String error) {}
