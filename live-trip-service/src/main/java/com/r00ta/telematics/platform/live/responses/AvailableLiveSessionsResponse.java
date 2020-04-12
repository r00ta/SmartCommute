package com.r00ta.telematics.platform.live.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.live.model.LiveSessionSummary;

public class AvailableLiveSessionsResponse {

    @JsonProperty("availableSessions")
    public List<LiveSessionSummary> sessions;

    public AvailableLiveSessionsResponse(List<LiveSessionSummary> sessions) {
        this.sessions = sessions;
    }
}
