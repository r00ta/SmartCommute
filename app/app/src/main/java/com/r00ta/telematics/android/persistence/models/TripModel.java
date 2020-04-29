package com.r00ta.telematics.android.persistence.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TripModel extends RealmObject {
    @PrimaryKey
    private String tripId;

    private String routeId;

    private Long startTimestamp;

    private boolean isFinished;
    private RealmList<GpsLocation> positions;
    private RealmList<ObdEngineRpmSample> engineRpmSamples;

    // ... Generated getters and setters ...

    public TripModel(){}

    public TripModel(String tripId, String routeId, Long startTimestamp){
        this.tripId = tripId;
        this.routeId = routeId;
        this.startTimestamp = startTimestamp;
        this.isFinished = false;
    }
}
