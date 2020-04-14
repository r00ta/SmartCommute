package com.r00ta.telematics.platform.users.messaging.live.dto;

import java.time.DayOfWeek;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveTripSummaryDto {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("routeId")
    public String routeId;

    @JsonProperty("transientTripId")
    public String transientTripId;

    @JsonProperty("day")
    public DayOfWeek day;

    @JsonProperty("urlLiveTrip")
    public String urlLiveTrip;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";
}
