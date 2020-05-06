package com.r00ta.telematics.models.livetrip;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailableLiveSessionsResponse {

    @JsonProperty("availableSessions")
    public List<LiveSessionSummary> sessions;

    public AvailableLiveSessionsResponse(List<LiveSessionSummary> sessions) {
        this.sessions = sessions;
    }

    public AvailableLiveSessionsResponse() {
    }
}