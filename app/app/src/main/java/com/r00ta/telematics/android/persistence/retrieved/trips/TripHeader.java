package com.r00ta.telematics.android.persistence.retrieved.trips;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TripHeader extends RealmObject {
    @PrimaryKey
    public Long id;

    public String startLocation;
    public String endLocation;
    public String startDate;
    public String endDate;
    public String tripDate;
    public String distance;
    public String tripId;
    public Long startTimestamp;

    public TripHeader(){}

    public TripHeader(Long id, String startLocation, String endLocation, String startDate, String endDate, String tripDate, String distance, String tripId, Long startTimestamp){
        this.id = id;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tripDate = tripDate;
        this.distance = distance;
        this.tripId = tripId;
        this.startTimestamp = startTimestamp;
    }
}
