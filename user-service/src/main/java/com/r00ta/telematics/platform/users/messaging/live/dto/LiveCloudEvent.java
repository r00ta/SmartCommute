package com.r00ta.telematics.platform.users.messaging.live.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveCloudEvent {

    @JsonProperty("data")
    public LiveTripCloudEvent data;

    @JsonProperty("id")
    public String id;

    @JsonProperty("source")
    public String source;

    @JsonProperty("specversion")
    public String specversion;

    @JsonProperty("type")
    public String type;
}
