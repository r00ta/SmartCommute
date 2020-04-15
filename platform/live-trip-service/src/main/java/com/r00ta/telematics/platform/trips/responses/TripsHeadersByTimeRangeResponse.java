package com.r00ta.telematics.platform.trips.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.trips.models.TripSummaryModel;

public class TripsHeadersByTimeRangeResponse {

    @JsonProperty("headers")
    public List<TripSummaryModel> headers;

    public TripsHeadersByTimeRangeResponse() {
    }

    public TripsHeadersByTimeRangeResponse(List<TripSummaryModel> headers) {
        this.headers = headers;
    }
}
