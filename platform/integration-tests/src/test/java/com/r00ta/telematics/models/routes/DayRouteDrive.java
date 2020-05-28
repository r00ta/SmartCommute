package com.r00ta.telematics.models.routes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
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

}
