package com.r00ta.telematics.platform.here.models.routing.attributes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpeedLimitAttribute {

    @JsonProperty("FROM_REF_SPEED_LIMIT")
    public String fromSpeedLimit;

    @JsonProperty("TO_REF_SPEED_LIMIT")
    public String toSpeedLimit;
}
