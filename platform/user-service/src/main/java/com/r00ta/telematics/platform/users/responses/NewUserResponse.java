package com.r00ta.telematics.platform.users.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewUserResponse {

    @JsonProperty("userId")
    public String userId;

    public NewUserResponse() {
    }

    public NewUserResponse(String userId) {
        this.userId = userId;
    }
}
