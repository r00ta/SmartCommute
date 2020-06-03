package com.r00ta.telematics.platform.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GpsLocation {
    @JsonProperty("latitude")
    public Double latitude;

    @JsonProperty("longitude")
    public Double longitude;
}
