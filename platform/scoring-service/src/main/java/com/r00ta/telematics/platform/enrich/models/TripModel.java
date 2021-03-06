package com.r00ta.telematics.platform.enrich.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.enrich.messaging.incoming.dto.TripModelDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripModel {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("tripId")
    public String tripId;

    @JsonProperty("routeId")
    public String routeId;

    @JsonProperty("positions")
    public List<GpsLocation> positions;

    @JsonProperty("engineRpmSamples")
    public List<ObdEngineRpmSample> engineRpmSamples;

    @JsonProperty("startTimestamp")
    public Long startTimestamp;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";

    public TripModel(TripModelDto dto) {
        this.userId = dto.userId;
        this.tripId = dto.tripId;
        this.routeId = dto.routeId;
        this.positions = dto.positions;
        this.engineRpmSamples = dto.engineRpmSamples;
        this.startTimestamp = dto.positions.get(0).timestamp;
    }

    public TripModel() {
    }
}
