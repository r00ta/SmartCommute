package com.r00ta.telematics.platform.users.messaging.enriched.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrichedTripSummaryDto {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("tripId")
    public String tripId;

    @JsonProperty("score")
    public Float score;

    @JsonProperty("distanceInM")
    public Double distanceInM;

    @JsonProperty("duration")
    public Long duration;

    @JsonProperty("co2Saved")
    public Double co2Saved;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";
}
