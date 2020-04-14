package com.r00ta.telematics.platform.trips.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.trips.models.TripModel;

public class TripsByTimeRangeResponse {

    @JsonProperty("trips")
    public List<TripModel> trips;

    public TripsByTimeRangeResponse() {
    }

    public TripsByTimeRangeResponse(List<TripModel> trips) {
        this.trips = trips;
    }
}
