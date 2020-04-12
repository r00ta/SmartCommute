package com.r00ta.telematics.platform.enrich.messaging;

import com.r00ta.telematics.platform.enrich.messaging.dto.CouldEvent;
import com.r00ta.telematics.platform.enrich.messaging.dto.TripCloudEvent;
import com.r00ta.telematics.platform.messaging.AbstractCloudEventDeserializer;

public class TripCloudEventDeserializer extends AbstractCloudEventDeserializer<CouldEvent> {

    public TripCloudEventDeserializer() {
        super(CouldEvent.class);
    }
}