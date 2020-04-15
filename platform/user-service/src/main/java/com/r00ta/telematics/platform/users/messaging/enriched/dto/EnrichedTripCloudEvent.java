package com.r00ta.telematics.platform.users.messaging.enriched.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrichedTripCloudEvent {

    @JsonProperty("event")
    public EnrichedTripSummaryDto event;
}
