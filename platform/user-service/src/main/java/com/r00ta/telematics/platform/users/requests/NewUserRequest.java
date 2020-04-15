package com.r00ta.telematics.platform.users.requests;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewUserRequest {

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
}
