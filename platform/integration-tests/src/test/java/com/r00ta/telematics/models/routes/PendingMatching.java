package com.r00ta.telematics.models.routes;

import java.time.DayOfWeek;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.models.livetrip.GpsLocation;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PendingMatching {

    @JsonProperty("matchingId")
    public String matchingId;

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("routeId")
    public String routeId;

    @JsonProperty("day")
    public DayOfWeek day;

    @JsonProperty("asPassenger")
    public boolean asPassenger;

    @JsonProperty("asDriver")
    public boolean asDriver;

    @JsonProperty("status")
    public MatchingPendingStatus status;

    @JsonProperty("startCandidateLocation")
    public GpsLocation startCandidateLocation;

    @JsonProperty("endCandidateLocation")
    public GpsLocation endCandidateLocation;

    @JsonProperty("matchedUserId")
    public String matchedUserId;

    @JsonProperty("matchedRouteId")
    public String matchedRouteId;
}
