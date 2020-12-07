package com.example.fireAuth_REST_API.exception;

public class NameNotAllowedException extends Exception{
    @Override
    public String getMessage() {
        return "User name = \"root\" is not allowed due to security reasons ";
    }
}
