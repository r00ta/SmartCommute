package com.r00ta.telematics.models.scoring;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrichedTripsByTimeRangeResponse {

    @JsonProperty("enrichedTripsHeaders")
    public List<EnrichedTripHeader> enrichedTrips;

    public EnrichedTripsByTimeRangeResponse() {
    }

    public EnrichedTripsByTimeRangeResponse(List<EnrichedTripHeader> enrichedTrips) {
        this.enrichedTrips = enrichedTrips;
    }
}
