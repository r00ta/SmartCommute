package com.r00ta.telematics.platform.enrich.messaging.incoming;

import com.r00ta.telematics.platform.enrich.messaging.incoming.dto.CloudEvent;
import com.r00ta.telematics.platform.messaging.AbstractCloudEventDeserializer;

public class TripCloudEventDeserializer extends AbstractCloudEventDeserializer<CloudEvent> {

    public TripCloudEventDeserializer() {
        super(CloudEvent.class);
    }
}