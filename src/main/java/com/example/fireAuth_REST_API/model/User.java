package com.example.fireAuth_REST_API.model;

import javax.validation.constraints.NotEmpty;

public class User {
    private String id;
    private String name;
//    @ValidateEmail(custRegex = "^[a-zA-Z0-9._]+@{1}[a-zA-Z0-9_]+[.]{1}[a-zA-Z0-9_]+[a-zA-Z0-9._]+$")
    @NotEmpty(message = "email Id cannot be null")
    private String email;
    @NotEmpty(message = "Please add a valid profile photo URL")
    private String profilePhotoUrl;

    public User(String name, String email, String profilePhotoUrl) {
        this.name = name;
        this.email = email;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }
}

