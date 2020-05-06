package com.r00ta.telematics.platform.trips.storage.codecs;

import com.r00ta.telematics.platform.live.models.LiveChunkModel;
import com.r00ta.telematics.platform.mongo.DocumentCodec;
import com.r00ta.telematics.platform.trips.models.TripSummaryModel;

public class TripSummaryModelCodecs extends DocumentCodec<TripSummaryModel> {


    public TripSummaryModelCodecs(Class<TripSummaryModel> clazz) {
        super(clazz);
    }
}
