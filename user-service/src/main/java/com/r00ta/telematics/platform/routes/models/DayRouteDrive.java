package com.r00ta.telematics.platform.routes.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DayRouteDrive {

    /**
     * All the routes are created as driver route.
     */
    @JsonProperty("isADriverRide")
    public boolean isADriverRide;

    @JsonProperty("isAPassengerRide")
    public boolean isAPassengerRoute;

    /**
     * present if isAPassengerRoute == true
     */
    @JsonProperty("driverReference")
    public DriverRideReference driverReference;

    /**
     * present if isAPassengerRoute == false
     */
    @JsonProperty("passengerIds")
    public List<PassengerRideReference> passengerReferences;

    public DayRouteDrive() {
        this.isADriverRide = true;
        this.passengerReferences = new ArrayList<>();
    }
}
