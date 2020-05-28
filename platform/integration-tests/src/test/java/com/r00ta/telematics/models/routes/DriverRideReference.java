package com.r00ta.telematics.models.routes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverRideReference {

    @JsonProperty("driverUserId")
    public String driverUserId;

    @JsonProperty("driverRouteId")
    public String driverRouteId;

    @JsonProperty("driverName")
    public String driverName;
}
