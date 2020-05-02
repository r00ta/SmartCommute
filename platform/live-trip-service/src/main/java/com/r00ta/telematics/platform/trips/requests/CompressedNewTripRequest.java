package com.r00ta.telematics.platform.trips.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompressedNewTripRequest {
    @JsonProperty("base64gzipNewTripRequest")
    public String base64gzipNewTripRequest;
}
