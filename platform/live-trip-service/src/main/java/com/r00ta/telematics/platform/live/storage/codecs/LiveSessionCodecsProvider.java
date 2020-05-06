package com.r00ta.telematics.platform.live.storage.codecs;

import com.r00ta.telematics.platform.live.models.LiveChunkModel;
import com.r00ta.telematics.platform.live.models.LiveSessionSummary;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class LiveSessionCodecsProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == LiveSessionSummary.class) {
            return (Codec<T>) new LiveSessionSummaryCodecs(LiveSessionSummary.class);
        }
        if (clazz == LiveChunkModel.class){
            return (Codec<T>) new LiveChunkModelCodecs(LiveChunkModel.class);
        }
        return null;
    }
}
