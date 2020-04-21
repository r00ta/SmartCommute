package com.r00ta.telematics.platform.models;

import com.r00ta.telematics.platform.messaging.model.CloudEventDto;

public class UserMatching implements CloudEventDto {

    @Override
    public String getEventId() {
        return null;
    }

    @Override
    public String getEventProducer() {
        return null;
    }
}
