package com.r00ta.telematics.models.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
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

    @JsonProperty("co2Saved")
    public Double co2Saved;

    public UserStatistics() {
    }
}