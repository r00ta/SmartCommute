package com.r00ta.telematics.models.routes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PassengerRideReference {

    @JsonProperty("passengerRouteId")
    public String passengerRouteId;

    @JsonProperty("passengerUserId")
    public String passengerUserId;

    @JsonProperty("passengerName")
    public String passengerName;
}
