package com.r00ta.telematics.platform.enrich.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.enrich.models.EnrichedTrip;
import com.r00ta.telematics.platform.enrich.models.EnrichedTripHeader;

public class EnrichedTripsByTimeRangeResponse {

    @JsonProperty("enrichedTripsHeaders")
    public List<EnrichedTripHeader> enrichedTrips;

    public EnrichedTripsByTimeRangeResponse() {
    }

    public EnrichedTripsByTimeRangeResponse(List<EnrichedTripHeader> enrichedTrips) {
        this.enrichedTrips = enrichedTrips;
    }
}
