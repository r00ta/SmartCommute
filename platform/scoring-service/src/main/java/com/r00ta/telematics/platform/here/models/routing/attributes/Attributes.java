package com.r00ta.telematics.platform.here.models.routing.attributes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attributes {

    @JsonProperty("SPEED_LIMITS_FCN")
    public List<SpeedLimitAttribute> speedLimitAttribute1s;
}
