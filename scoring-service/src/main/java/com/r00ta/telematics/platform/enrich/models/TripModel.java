package com.r00ta.telematics.platform.enrich.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.enrich.messaging.incoming.dto.TripModelDto;

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

    public TripModel(TripModelDto dto) {
        this.userId = dto.userId;
        this.tripId = dto.tripId;
        this.positions = dto.positions;
        this.startTimestamp = dto.positions.get(0).timestamp;
    }

    public TripModel() {
    }
}
