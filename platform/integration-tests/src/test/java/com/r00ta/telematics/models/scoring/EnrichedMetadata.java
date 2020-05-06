package com.r00ta.telematics.models.scoring;

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
}