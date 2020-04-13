package com.r00ta.telematics.platform.routes.models;

import java.time.DayOfWeek;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RouteHeader {

    @JsonProperty("routeId")
    public String routeId;

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("days")
    public List<DayOfWeek> days;

    @JsonProperty("startPositionUserLabel")
    public String startPositionUserLabel;

    @JsonProperty("endPositionUserLabel")
    public String endPositionUserLabel;

    @JsonProperty("daysAsDriver")
    public Integer daysAsDriver;

    @JsonProperty("daysAsPassenger")
    public Integer daysAsPassenger;

    public RouteHeader() {
    }

    public RouteHeader(Route route) {
        this.routeId = route.routeId;
        this.userId = route.userId;
        this.days = route.days;
        this.startPositionUserLabel = route.startPositionUserLabel;
        this.endPositionUserLabel = route.endPositionUserLabel;
        this.daysAsDriver = Math.toIntExact(route.dayRides.values().stream().filter(x -> x.isADriverRide).count());
        this.daysAsPassenger = Math.toIntExact(route.dayRides.values().stream().filter(x -> x.isAPassengerRoute).count());
    }
}
