package com.r00ta.telematics.platform.trips.messaging.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.messaging.model.CloudEventDto;
import com.r00ta.telematics.platform.trips.models.GpsLocation;
import com.r00ta.telematics.platform.trips.models.TripModel;

public class TripModelDto implements CloudEventDto {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("tripId")
    public String tripId;

    @JsonProperty("positions")
    public List<GpsLocation> positions;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";

    public TripModelDto() {
    }

    public TripModelDto(TripModel model) {
        this.userId = model.userId;
        this.tripId = model.tripId;
        this.positions = model.positions;
    }

    @Override
    public String getEventId() {
        return tripId;
    }

    @Override
    public String getEventProducer() {
        return "TripService";
    }
}
