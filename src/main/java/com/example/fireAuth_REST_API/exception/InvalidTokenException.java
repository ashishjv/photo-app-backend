package com.example.fireAuth_REST_API.exception;

public class InvalidTokenException extends Exception{

    @Override
    public String getMessage() {
        return "Invalid idToken Custom Exception";
    }
}
