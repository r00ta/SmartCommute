package com.r00ta.telematics.platform.routes.storage.codecs;

import com.r00ta.telematics.platform.mongo.DocumentCodec;
import com.r00ta.telematics.platform.routes.models.PendingMatching;

public class MatchingsCodecs extends DocumentCodec<PendingMatching> {

    public MatchingsCodecs(Class<PendingMatching> clazz) {
        super(clazz);
    }
}
