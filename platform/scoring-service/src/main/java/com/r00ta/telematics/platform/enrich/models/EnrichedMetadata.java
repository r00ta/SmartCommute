package com.r00ta.telematics.platform.enrich.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrichedMetadata {

    /**
     * Speed limit in Mps
     */
    @JsonProperty("speedLimit")
    public Double speedLimit;

    public EnrichedMetadata() {
    }

    /**
     * Speed limits from here comes as strings, in Km/h
     */
    public EnrichedMetadata(String speedLimit) {
        this.speedLimit = Float.valueOf(speedLimit) / 3.6;
    }
}
