package com.r00ta.telematics.platform.users.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserStatistics {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("totalDistanceDrivenInM")
    public Double totalDistanceDrivenInM;

    @JsonProperty("totalDurationDriven")
    public Double totalDurationDriven;

    @JsonProperty("drivingScore")
    public Double drivingScore;

    // What do we do here for passengers?
    @JsonProperty("totalDistanceAsPassengerInM")
    public Double totalDistanceAsPassengerInM;

    @JsonProperty("totalDurationAsPassenger")
    public Double totalDurationAsPassenger;

    @JsonProperty("passengerScore")
    public Double passengerScore;

    @JsonProperty("communityScore")
    public Double communityScore;

    @JsonProperty("numberOfReviews")
    public Long numberOfReviews;

    public UserStatistics() {
    }

    public UserStatistics(String userId) {
        this.userId = userId;
        this.totalDistanceAsPassengerInM = 0.0d;
        this.totalDurationDriven = 0.0d;
        this.totalDistanceDrivenInM = 0.0d;
        this.drivingScore = 0.0d;
        this.totalDurationAsPassenger = 0.0d;
        this.passengerScore = 0.0d;
        this.communityScore = 0.0d;
        this.numberOfReviews = 0L;
    }
}
