package com.r00ta.telematics.platform.routes.models;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public DriverPreferences(boolean availableAsDriver, Float radiusStartPoint, boolean onTheRouteOption, Long flexibility) {
        this.availableAsDriver = availableAsDriver;
        this.radiusStartPoint = radiusStartPoint;
        this.onTheRouteOption = onTheRouteOption;
        this.flexibility = flexibility;
    }
}
