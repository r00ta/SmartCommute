package com.r00ta.telematics.platform.routes.requests;

import java.time.DayOfWeek;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.routes.models.DriverPreferences;

public class NewRouteRequest {

    @JsonProperty("days")
    public List<DayOfWeek> days;

    @JsonProperty("availableAsPassenger")
    public boolean availableAsPassenger;

    @JsonProperty("driverPreferences")
    public DriverPreferences driverPreferences;

    @JsonProperty("startPositionUserLabel")
    public String startPositionUserLabel;

    @JsonProperty("endPositionUserLabel")
    public String endPositionUserLabel;
}
