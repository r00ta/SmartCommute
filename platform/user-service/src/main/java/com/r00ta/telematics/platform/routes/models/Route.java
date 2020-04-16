package com.r00ta.telematics.platform.routes.models;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.routes.requests.NewRouteRequest;

public class Route {

    @JsonProperty("routeId")
    public String routeId;

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("days")
    public List<DayOfWeek> days;

    @JsonProperty("availableAsPassenger")
    public boolean availableAsPassenger;

    @JsonProperty("driverPreferences")
    public DriverPreferences driverPreferences;

    @JsonProperty
    public HashMap<DayOfWeek, DayRouteDrive> dayRides;

    @JsonProperty("startPositionUserLabel")
    public String startPositionUserLabel;

    @JsonProperty("endPositionUserLabel")
    public String endPositionUserLabel;

    public Route() {
    }

    public Route(String userId, NewRouteRequest routeRequest, String routeId) {
        this.routeId = routeId;
        this.userId = userId;
        this.days = routeRequest.days;
        this.availableAsPassenger = routeRequest.availableAsPassenger;
        this.driverPreferences = routeRequest.driverPreferences;
        this.dayRides = new HashMap<>();
        this.days.stream().forEach(x -> this.dayRides.put(x, DayRouteDrive.createDefault()));
    }

    public String findPassengerRouteId(String passengerUserId) {
        for (DayRouteDrive dayRide : dayRides.values()) {
            Optional<PassengerRideReference> res = dayRide.passengerReferences.stream().filter(x -> x.passengerUserId.equals(passengerUserId)).findFirst();
            if (res.isPresent()) {
                return res.get().passengerRouteId;
            }
        }

        throw new IllegalArgumentException("passengerId not found");
    }

    public String findDriverRouteId(String driverUserId) {
        for (DayRouteDrive dayRide : dayRides.values()) {
            if (dayRide.driverReference.driverUserId.equals(driverUserId)) {
                return dayRide.driverReference.driverRouteId;
            }
        }

        throw new IllegalArgumentException("driverUserId not found");
    }
}
