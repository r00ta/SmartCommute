package com.r00ta.telematics.models.routes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AvailableMatchingsResponse {

    @JsonProperty("matchings")
    public List<PendingMatching> matchings;
}