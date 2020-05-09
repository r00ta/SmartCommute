package com.r00ta.telematics.android.persistence.retrieved.trips;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.android.network.models.trips.EnrichedGpsLocation;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EnrichedGpsPosition extends RealmObject {
    @JsonProperty("longitude")
    public Float longitude;

    @JsonProperty("latitude")
    public Float latitude;

    @JsonProperty("elevation")
    public Float elevation;

    @JsonProperty("speedMps")
    public Float speedMps;

    @JsonProperty("confidenceValue")
    public Float confidenceValue;

    @JsonProperty("timestamp")
    @PrimaryKey
    public Long timestamp;

    @JsonProperty("speedLimit")
    public Double speedLimit;

    @JsonProperty("pointScore")
    public Float pointScore;

    public static EnrichedGpsPosition fromDto(EnrichedGpsLocation location){
        EnrichedGpsPosition position = new EnrichedGpsPosition();
        position.longitude = location.longitude;
        position.latitude = location.latitude;
        position.elevation = location.elevation;
        position.speedMps = location.speedMps;
        position.confidenceValue = location.confidenceValue;
        position.timestamp = location.timestamp;
        position.speedLimit = location.metadata.speedLimit;
        position.pointScore = location.pointScore;

        return position;
    }
}
