package com.r00ta.telematics.platform.trips.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TripModel {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("tripId")
    public String tripId;

    @JsonProperty("positions")
    public List<GpsLocation> positions;

    @JsonProperty("startTimestamp")
    public Long startTimestamp;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";

    public TripModel() {
    }

    public TripModel(String userId, String tripId, Long startTimestamp,  List<GpsLocation> positions) {
        this.userId = userId;
        this.tripId = tripId;
        this.startTimestamp = startTimestamp;
        this.positions = positions;
    }
}
