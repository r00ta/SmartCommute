package com.r00ta.telematics.android.network.models.trips;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrichedMetadata {
    @JsonProperty("speedLimit")
    public Double speedLimit;
}
