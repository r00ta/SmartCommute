package com.r00ta.telematics.platform.messaging.incoming.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteCloudEvent {

    @JsonProperty("event")
    public RouteModelDto event;
}
