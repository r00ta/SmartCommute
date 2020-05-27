package com.r00ta.telematics.platform.routes.messaging.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchingCloudEvent {

    @JsonProperty("event")
    public MatchingModelDto event;
}
