package com.r00ta.telematics.platform.routes.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PassengerRideReference {

    @JsonProperty("passengerRouteId")
    public String passengerRouteId;

    @JsonProperty("passengerUserId")
    public String passengerUserId;

    @JsonProperty("passengerName")
    public String passengerName;

    public PassengerRideReference(){}

    public PassengerRideReference(String passengerUserId, String passengerRouteId, String passengerName){
        this.passengerRouteId = passengerRouteId;
        this.passengerUserId = passengerUserId;
        this.passengerName = passengerName;
    }
}
