package com.r00ta.telematics.platform.messaging.incoming.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.models.EnrichedGpsLocation;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteModelDto {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("routeId")
    public String routeId;

    @JsonProperty("positions")
    public List<EnrichedGpsLocation> positions;

    @JsonProperty("startTimestamp")
    public Long startTimestamp;
}
