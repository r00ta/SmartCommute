package com.r00ta.telematics.platform.messaging.outgoing.dto;

import java.time.DayOfWeek;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.messaging.model.CloudEventDto;
import com.r00ta.telematics.platform.models.GpsLocation;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchingModelDto implements CloudEventDto {
    @JsonProperty("matchingId")
    public String matchingId;

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

    @Override
    public String getEventId() {
        return matchingId;
    }

    @Override
    public String getEventProducer() {
        return "RouteAnalyticsService";
    }
}