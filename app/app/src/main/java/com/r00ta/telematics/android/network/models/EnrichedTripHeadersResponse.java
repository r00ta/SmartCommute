package com.r00ta.telematics.android.network.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrichedTripHeadersResponse {
    @JsonProperty("enrichedTripsHeaders")
    public List<EnrichedTripHeader> enrichedTrips;
}
