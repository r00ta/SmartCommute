package com.r00ta.telematics.platform.routes.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.routes.models.PendingMatching;

public class AvailableMatchingsResponse {

    @JsonProperty("matchings")
    public List<PendingMatching> matchings;

    public AvailableMatchingsResponse() {
    }

    public AvailableMatchingsResponse(List<PendingMatching> matchings) {
        this.matchings = matchings;
    }
}
