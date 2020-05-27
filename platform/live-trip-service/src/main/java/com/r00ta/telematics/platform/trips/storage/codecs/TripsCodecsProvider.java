package com.r00ta.telematics.platform.trips.storage.codecs;

import com.r00ta.telematics.platform.live.models.LiveChunkModel;
import com.r00ta.telematics.platform.live.models.LiveSessionSummary;
import com.r00ta.telematics.platform.trips.models.TripModel;
import com.r00ta.telematics.platform.trips.models.TripSummaryModel;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class TripsCodecsProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == TripSummaryModel.class) {
            return (Codec<T>) new TripSummaryModelCodecs(TripSummaryModel.class);
        }
        if (clazz == TripModel.class){
            return (Codec<T>) new TripModelCodecs(TripModel.class);
        }
        return null;
    }
}
