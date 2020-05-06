package com.r00ta.telematics.platform.enrich.storage.codecs;

import com.r00ta.telematics.platform.enrich.models.EnrichedTrip;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class EnrichedTripCodecsProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == EnrichedTrip.class) {
            return (Codec<T>) new EnrichedTripCodecs(EnrichedTrip.class);
        }
        return null;
    }
}
