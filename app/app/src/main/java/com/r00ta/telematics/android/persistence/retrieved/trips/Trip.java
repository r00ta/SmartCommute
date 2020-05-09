package com.r00ta.telematics.android.persistence.retrieved.trips;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.android.network.models.trips.EnrichedTripResponse;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Trip extends RealmObject {
    @JsonProperty("userId")
    public String userId;

    @JsonProperty("tripId")
    @PrimaryKey
    public String tripId;

    @JsonProperty("routeId")
    public String routeId;

    @JsonProperty("positions")
    public RealmList<EnrichedGpsPosition> positions = new RealmList<>();

    @JsonProperty("startTimestamp")
    public Long startTimestamp;

    @JsonProperty("durationInMilliseconds")
    public Long durationInMilliseconds;

    @JsonProperty("distanceInM")
    public Float distanceInM;

    @JsonProperty("score")
    public Float score;

    @JsonProperty("startLocation")
    public String startLocation;

    @JsonProperty("endLocation")
    public String endLocation;

    public static Trip fromDto(EnrichedTripResponse dto) {
        Trip trip = new Trip();
        trip.userId = dto.userId;
        trip.tripId = dto.tripId;
        trip.routeId = dto.routeId;
        trip.startTimestamp = dto.startTimestamp;
        trip.durationInMilliseconds = dto.durationInMilliseconds;
        trip.distanceInM = dto.distanceInM;
        trip.score = dto.score;
        trip.startLocation = dto.startLocation;
        trip.endLocation = dto.endLocation;

        return trip;
    }
}
