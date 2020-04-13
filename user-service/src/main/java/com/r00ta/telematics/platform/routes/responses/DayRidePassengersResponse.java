package com.r00ta.telematics.platform.routes.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DayRidePassengersResponse {

    @JsonProperty("passengers")
    public List<String> passengers;

    public DayRidePassengersResponse() {
    }

    public DayRidePassengersResponse(List<String> passengers) {
        this.passengers = passengers;
    }
}
