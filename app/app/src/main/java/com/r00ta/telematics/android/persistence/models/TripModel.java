package com.r00ta.telematics.android.persistence.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TripModel extends RealmObject {
    @PrimaryKey
    public String tripId;

    public String routeId;

    public Long startTimestamp;

    public boolean isFinished;

    public RealmList<GpsLocation> positions = new RealmList<>();

    public RealmList<ObdEngineRpmSample> engineRpmSamples = new RealmList<>();

    public int transformationFailures;

    // ... Generated getters and setters ...

    public TripModel(){}

    public TripModel(String tripId, String routeId, Long startTimestamp){
        this.tripId = tripId;
        this.routeId = routeId;
        this.startTimestamp = startTimestamp;
        this.isFinished = false;
        this.transformationFailures = 0;
    }
}
