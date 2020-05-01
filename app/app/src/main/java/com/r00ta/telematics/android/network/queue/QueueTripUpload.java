package com.r00ta.telematics.android.network.queue;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class QueueTripUpload extends RealmObject {
    @PrimaryKey
    public String tripId;

    public String userId;

    public String compressedTrip;

    public Long startTimestamp;

    public boolean isPending;

    public QueueTripUpload(){}

    public QueueTripUpload(String tripId, String userId, String compressedTrip, Long startTimestamp){
        this.tripId = tripId;
        this.userId = userId;
        this.compressedTrip = compressedTrip;
        this.startTimestamp = startTimestamp;
        this.isPending = false;
    }
}
