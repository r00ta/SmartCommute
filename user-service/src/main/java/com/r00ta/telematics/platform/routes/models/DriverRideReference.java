package com.r00ta.telematics.platform.routes.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DriverRideReference {

    @JsonProperty("driverUserId")
    public String driverUserId;

    @JsonProperty("driverRouteId")
    public String driverRouteId;

    @JsonProperty("driverName")
    public String driverName;

    public DriverRideReference() {
    }

    public DriverRideReference(String driverUserId, String driverRouteId, String driverName) {
        this.driverUserId = driverUserId;
        this.driverRouteId = driverRouteId;
        this.driverName = driverName;
    }
}
