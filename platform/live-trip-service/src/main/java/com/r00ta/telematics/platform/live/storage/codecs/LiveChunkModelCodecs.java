package com.r00ta.telematics.platform.live.storage.codecs;

import com.r00ta.telematics.platform.live.models.LiveChunkModel;
import com.r00ta.telematics.platform.live.models.LiveSessionSummary;
import com.r00ta.telematics.platform.mongo.DocumentCodec;

public class LiveChunkModelCodecs extends DocumentCodec<LiveChunkModel> {


    public LiveChunkModelCodecs(Class<LiveChunkModel> clazz) {
        super(clazz);
    }
}
