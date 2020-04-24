package com.r00ta.telematics.platform.trips.requests;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.trips.models.GpsLocation;
import com.r00ta.telematics.platform.trips.models.ObdEngineRpmSample;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewTripRequest {
    @JsonProperty("routeId")
    public String routeId;

    @JsonProperty("startTimestamp")
    public Long startTimestamp;

    @JsonProperty("positions")
    public List<GpsLocation> positions;

    @JsonProperty("engineRpmSamples")
    public List<ObdEngineRpmSample> engineRpmSamples;
}
