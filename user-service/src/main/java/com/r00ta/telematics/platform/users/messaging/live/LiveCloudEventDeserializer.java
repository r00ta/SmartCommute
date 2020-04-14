package com.r00ta.telematics.platform.users.messaging.live;

import com.r00ta.telematics.platform.messaging.AbstractCloudEventDeserializer;
import com.r00ta.telematics.platform.users.messaging.live.dto.LiveCloudEvent;

public class LiveCloudEventDeserializer extends AbstractCloudEventDeserializer<LiveCloudEvent> {

    public LiveCloudEventDeserializer() {
        super(LiveCloudEvent.class);
    }
}