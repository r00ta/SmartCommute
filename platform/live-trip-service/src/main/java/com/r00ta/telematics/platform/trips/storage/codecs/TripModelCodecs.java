package com.r00ta.telematics.platform.trips.storage.codecs;

import com.r00ta.telematics.platform.live.models.LiveSessionSummary;
import com.r00ta.telematics.platform.mongo.DocumentCodec;
import com.r00ta.telematics.platform.trips.models.TripModel;

public class TripModelCodecs extends DocumentCodec<TripModel> {

    public TripModelCodecs(Class<TripModel> clazz) {
        super(clazz);
    }
}
