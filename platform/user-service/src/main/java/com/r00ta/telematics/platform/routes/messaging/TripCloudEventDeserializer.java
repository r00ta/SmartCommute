package com.r00ta.telematics.platform.routes.messaging;

import com.r00ta.telematics.platform.messaging.AbstractCloudEventDeserializer;
import com.r00ta.telematics.platform.routes.messaging.dto.CloudEvent;

public class TripCloudEventDeserializer extends AbstractCloudEventDeserializer<CloudEvent> {

    public TripCloudEventDeserializer() {
        super(CloudEvent.class);
    }
}