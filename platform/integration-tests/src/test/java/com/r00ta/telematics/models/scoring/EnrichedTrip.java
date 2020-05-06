package com.r00ta.telematics.models.scoring;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrichedTrip {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("tripId")
    public String tripId;

    @JsonProperty("routeId")
    public String routeId;

    @JsonProperty("positions")
    public List<EnrichedGpsLocation> positions;

    @JsonProperty("startTimestamp")
    public Long startTimestamp;

    @JsonProperty("durationInMilliseconds")
    public Long durationInMilliseconds;

    @JsonProperty("distanceInM")
    public Float distanceInM;

    @JsonProperty("score")
    public Float score;

    @JsonProperty("startLocation")
    public String startLocation;

    @JsonProperty("endLocation")
    public String endLocation;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";

    public EnrichedTrip() {
    }
}