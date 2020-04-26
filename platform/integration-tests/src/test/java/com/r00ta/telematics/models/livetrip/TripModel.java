package com.r00ta.telematics.models.livetrip;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TripModel {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("tripId")
    public String tripId;

    @JsonProperty("routeId")
    public String routeId;

    @JsonProperty("positions")
    public List<GpsLocation> positions;

    @JsonProperty("startTimestamp")
    public Long startTimestamp;

    @JsonProperty("engineRpmSamples")
    public List<ObdEngineRpmSample> engineRpmSamples;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";

    public TripModel() {
    }
}
