package com.r00ta.telematics.android.persistence.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TripModel extends RealmObject {
    @PrimaryKey
    private String tripId;
    private String userId;
    private String routeId;
    public Long startTimestamp;

    public RealmList<GpsLocation> positions;
    public RealmList<ObdEngineRpmSample> engineRpmSamples;

    // ... Generated getters and setters ...

}
