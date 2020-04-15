package com.r00ta.telematics.platform.users.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.users.requests.NewUserRequest;

public class User {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("name")
    public String name;

    @JsonProperty("surename")
    public String surename;

    @JsonProperty("email")
    public String email;

    @JsonProperty("passwordHash")
    public String passwordHash;

    @JsonProperty("birthDay")
    public Date birthDay;

    // put them in another document
    // TODO
    @JsonProperty("reviews")
    public List<Review> reviews;

    public User() {
    }

    public User(NewUserRequest userRequest, String userId) {
        this.userId = userId;
        this.name = userRequest.name;
        this.surename = userRequest.surename;
        this.email = userRequest.email;
        this.passwordHash = userRequest.passwordHash;
        this.birthDay = userRequest.birthDay;
        this.reviews = new ArrayList<>();
    }
}
