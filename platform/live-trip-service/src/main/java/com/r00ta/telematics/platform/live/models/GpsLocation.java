package com.r00ta.telematics.platform.live.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GpsLocation {

    @JsonProperty("timestamp")
    public Long timestamp;

    @JsonProperty("latitude")
    public Double latitude;

    @JsonProperty("longitude")
    public Double longitude;

    @JsonProperty("accuracy")
    public Double accuracy;

    @JsonProperty("speed")
    public Float speed;

    @JsonProperty("bearing")
    public Float bearing;

    @JsonProperty("elevation")
    public Float elevation;

    public GpsLocation() {
    }
}
