package com.r00ta.telematics.platform.users.messaging.enriched;

import com.r00ta.telematics.platform.messaging.AbstractCloudEventDeserializer;
import com.r00ta.telematics.platform.users.messaging.enriched.dto.EnrichedCloudEvent;

public class ScoreCloudEventDeserializer extends AbstractCloudEventDeserializer<EnrichedCloudEvent> {

    public ScoreCloudEventDeserializer() {
        super(EnrichedCloudEvent.class);
    }
}