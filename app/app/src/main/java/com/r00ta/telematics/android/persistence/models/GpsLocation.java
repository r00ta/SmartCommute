package com.r00ta.telematics.android.persistence.models;

import io.realm.RealmObject;

public class GpsLocation  extends RealmObject {
    public Long timestamp;

    public Double latitude;

    public Double longitude;

    public Double accuracy;

    public Float speed;

    public Float bearing;

    public Float elevation;

    public GpsLocation() {
    }
}
