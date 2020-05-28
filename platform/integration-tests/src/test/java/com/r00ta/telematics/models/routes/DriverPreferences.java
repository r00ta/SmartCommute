package com.r00ta.telematics.models.routes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverPreferences {

    @JsonProperty("availableAsDriver")
    public boolean availableAsDriver;

    /**
     * 0 if not available
     */
    @JsonProperty("radiusStartPoint")
    public Float radiusStartPoint;

    /**
     * 0 if not available
     */
    @JsonProperty("onTheRouteOption")
    public boolean onTheRouteOption;

    /**
     * Flexibility in time?
     */
    @JsonProperty("flexibility")
    public Long flexibility;

    public DriverPreferences() {
    }
}