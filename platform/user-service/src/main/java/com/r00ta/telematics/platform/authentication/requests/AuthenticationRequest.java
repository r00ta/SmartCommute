package com.r00ta.telematics.platform.authentication.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationRequest {
    @JsonProperty("email")
    public String email;

    @JsonProperty("password")
    public String password;
}
