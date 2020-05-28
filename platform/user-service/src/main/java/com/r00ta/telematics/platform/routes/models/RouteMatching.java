package com.r00ta.telematics.platform.routes.models;

import java.time.DayOfWeek;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.routes.messaging.dto.MatchingModelDto;

public class RouteMatching {

    @JsonProperty("driverUserId")
    public String driverUserId;

    @JsonProperty("passengerUserId")
    public String passengerUserId;

    @JsonProperty("driverRouteId")
    public String driverRouteId;

    @JsonProperty("passengerRouteId")
    public String passengerRouteId;

    @JsonProperty("startLocationPickUp")
    public GpsLocation startLocationPickUp;

    @JsonProperty("endLocationDropOff")
    public GpsLocation endLocationDropOff;

    @JsonProperty("day")
    public DayOfWeek day;

    public static RouteMatching fromEvent(MatchingModelDto event) {
        return new RouteMatching(event.driverUserId, event.driverRouteId, event.passengerUserId, event.passengerRouteId, event.startLocationPickUp, event.endLocationDropOff, event.day);
    }

    public RouteMatching(String driverUserId, String driverRouteId, String passengerUserId, String passengerRouteId, GpsLocation start, GpsLocation end, DayOfWeek day){
        this.driverUserId = driverUserId;
        this.driverRouteId = driverRouteId;
        this.passengerUserId = passengerUserId;
        this.passengerRouteId  = passengerRouteId;
        this.startLocationPickUp = start;
        this.endLocationDropOff = end;
        this.day = day;
    }
}