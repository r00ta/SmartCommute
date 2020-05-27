package com.r00ta.telematics.platform.routes.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.routes.models.MatchingPendingStatus;

public class UpdatePendingMatchingRequest {

    @JsonProperty("status")
    public MatchingPendingStatus status;
}
