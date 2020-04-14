package com.r00ta.telematics.platform.users.messaging.live.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveTripCloudEvent {

    @JsonProperty("event")
    public LiveTripSummaryDto event;
}
