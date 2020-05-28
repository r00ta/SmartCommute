package com.r00ta.telematics.models.routes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailableMatchingsResponse {

    @JsonProperty("matchings")
    public List<PendingMatching> matchings;
}