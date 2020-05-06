package com.r00ta.telematics.models.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationResponse {

    @JsonProperty("jwtBearer")
    public String jwtBearer;

    @JsonProperty("userId")
    public String userId;

    public AuthenticationResponse(String userId, String jwtBearer) {
        this.userId = userId;
        this.jwtBearer = jwtBearer;
    }

    public AuthenticationResponse() {
    }
}