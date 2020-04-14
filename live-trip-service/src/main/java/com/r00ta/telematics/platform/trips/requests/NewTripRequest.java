package com.r00ta.telematics.platform.trips.requests;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.trips.models.GpsLocation;

public class NewTripRequest {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("tripId")
    public String tripId;

    @JsonProperty("startTimestamp")
    public Long startTimestamp;

    @JsonProperty("positions")
    public List<GpsLocation> positions;
}
