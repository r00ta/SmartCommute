package com.r00ta.telematics.platform.messaging.incoming;

import com.r00ta.telematics.platform.messaging.AbstractCloudEventDeserializer;
import com.r00ta.telematics.platform.messaging.incoming.dto.CloudEvent;

public class RouteEventDeserializer extends AbstractCloudEventDeserializer<CloudEvent> {

    public RouteEventDeserializer() {
        super(CloudEvent.class);
    }
}