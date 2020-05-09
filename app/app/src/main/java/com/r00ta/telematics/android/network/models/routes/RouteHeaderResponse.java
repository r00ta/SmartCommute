package com.r00ta.telematics.android.network.models.routes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.DayOfWeek;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteHeaderResponse {
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
}
