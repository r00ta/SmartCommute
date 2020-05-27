package com.r00ta.telematics.platform.routes.messaging.dto;

import java.time.DayOfWeek;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.routes.models.GpsLocation;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchingModelDto {

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

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";
}
