package com.example.fireAuth_REST_API.exception;

public class UserNotAuthorizedException extends Exception {

    private String CustomString;

    public UserNotAuthorizedException(){
        this.CustomString = "";
    }
    public UserNotAuthorizedException(String customString) {
        this.CustomString = customString;
    }

    public String getCustomString() {
        return CustomString;
    }

    public void setCustomString(String customString) {
        this.CustomString = customString;
    }

    @Override
    public String getMessage() {
        return "User not authorized for this operation. Email ID does not match - \n" + this.CustomString;
    }

}