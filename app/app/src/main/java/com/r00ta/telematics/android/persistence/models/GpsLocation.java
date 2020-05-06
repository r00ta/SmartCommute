package com.r00ta.telematics.android.persistence.models;

import io.realm.RealmObject;

public class GpsLocation  extends RealmObject {
    public Long timestamp;

    public Double latitude;

    public Double longitude;

    public float accuracy;

    public Float speed;

    public Float bearing;

    public double elevation;

    public GpsLocation() {
    }
}
