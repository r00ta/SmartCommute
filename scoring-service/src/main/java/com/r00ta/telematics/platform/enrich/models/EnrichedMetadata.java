package com.r00ta.telematics.platform.enrich.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnrichedMetadata {

    /**
     * Speed limit in Mps
     */
    @JsonProperty("FROM_REF_SPEED_LIMIT")
    public Double fromSpeedLimit;

    /**
     * Speed limit in Mps
     */
    @JsonProperty("TO_REF_SPEED_LIMIT")
    public Double toSpeedLimit;

    public EnrichedMetadata() {
    }

    /**
     * Speed limits from here comes as strings, in Km/h
     */
    public EnrichedMetadata(String fromSpeedLimit, String toSpeedLimit) {
        this.fromSpeedLimit = Float.valueOf(fromSpeedLimit) / 3.6;
        this.toSpeedLimit = Float.valueOf(toSpeedLimit) / 3.6;
    }
}
