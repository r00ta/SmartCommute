package com.r00ta.telematics.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {
    @JsonProperty("jwtBearer")
    public String jwtBearer;

    @JsonProperty("userId")
    public String userId;

    public AuthenticationResponse(String userId, String jwtBearer){
        this.userId = userId;
        this.jwtBearer = jwtBearer;
    }
}