package com.r00ta.telematics.platform.enrich.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ObdEngineRpmSample {

    @JsonProperty("timestamp")
    public Long timestmap;

    @JsonProperty("value")
    public Long value;
}
