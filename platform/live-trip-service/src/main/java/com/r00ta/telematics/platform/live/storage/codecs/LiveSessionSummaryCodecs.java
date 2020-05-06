package com.r00ta.telematics.platform.live.storage.codecs;

import com.r00ta.telematics.platform.live.models.LiveSessionSummary;
import com.r00ta.telematics.platform.mongo.DocumentCodec;

public class LiveSessionSummaryCodecs extends DocumentCodec<LiveSessionSummary> {

    public LiveSessionSummaryCodecs(Class<LiveSessionSummary> clazz) {
        super(clazz);
    }
}
