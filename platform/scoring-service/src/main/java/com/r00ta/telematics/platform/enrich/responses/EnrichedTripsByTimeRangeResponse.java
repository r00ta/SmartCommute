package com.r00ta.telematics.platform.enrich.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.enrich.models.EnrichedTrip;

public class EnrichedTripsByTimeRangeResponse {

    @JsonProperty("enrichedTrips")
    public List<EnrichedTrip> enrichedTrips;

    public EnrichedTripsByTimeRangeResponse() {
    }

    public EnrichedTripsByTimeRangeResponse(List<EnrichedTrip> enrichedTrips) {
        this.enrichedTrips = enrichedTrips;
    }
}
