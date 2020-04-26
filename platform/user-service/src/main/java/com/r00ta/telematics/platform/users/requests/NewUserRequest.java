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
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public Date birthDay;
}
