package com.r00ta.telematics.platform.enrich.messaging.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripCloudEvent {

    @JsonProperty("event")
    public TripModelDto event;
}
