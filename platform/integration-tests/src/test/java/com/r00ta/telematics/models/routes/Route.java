package com.r00ta.telematics.models.routes;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
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
}
