package com.r00ta.telematics.platform.routes.models;

import java.time.DayOfWeek;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.SmartDocument;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PendingMatching extends SmartDocument {

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

    public PendingMatching(){}

    public PendingMatching(String matchingId, String userId, String routeId, DayOfWeek day, boolean asPassenger, MatchingPendingStatus status, GpsLocation startCandidateLocation, GpsLocation endCandidateLocation, String matchedUserId, String matchedRouteId){
        this.matchingId = matchingId;
        this.userId = userId;
        this.routeId = routeId;
        this.day = day;
        this.asPassenger = asPassenger;
        this.asDriver = !asPassenger;
        this.status = status;
        this.startCandidateLocation = startCandidateLocation;
        this.endCandidateLocation = endCandidateLocation;
        this.matchedUserId = matchedUserId;
        this.matchedRouteId = matchedRouteId;
    }
}
