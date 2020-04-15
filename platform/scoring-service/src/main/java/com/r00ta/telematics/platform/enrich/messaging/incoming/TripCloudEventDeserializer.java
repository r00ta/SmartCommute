package com.r00ta.telematics.platform.enrich.messaging.incoming;

import com.r00ta.telematics.platform.enrich.messaging.incoming.dto.CouldEvent;
import com.r00ta.telematics.platform.messaging.AbstractCloudEventDeserializer;

public class TripCloudEventDeserializer extends AbstractCloudEventDeserializer<CouldEvent> {

    public TripCloudEventDeserializer() {
        super(CouldEvent.class);
    }
}