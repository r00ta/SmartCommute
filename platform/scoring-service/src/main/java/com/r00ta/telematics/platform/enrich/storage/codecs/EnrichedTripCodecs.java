package com.r00ta.telematics.platform.enrich.storage.codecs;

import com.r00ta.telematics.platform.enrich.models.EnrichedTrip;
import com.r00ta.telematics.platform.mongo.DocumentCodec;

public class EnrichedTripCodecs extends DocumentCodec<EnrichedTrip> {

    public EnrichedTripCodecs(Class<EnrichedTrip> clazz) {
        super(clazz);
    }
}
