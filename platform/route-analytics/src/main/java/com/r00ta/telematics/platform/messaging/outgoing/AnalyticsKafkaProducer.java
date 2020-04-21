package com.r00ta.telematics.platform.messaging.outgoing;

import com.r00ta.telematics.platform.messaging.AbstractKafkaProducer;
import com.r00ta.telematics.platform.models.UserMatching;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.reactivestreams.Publisher;

public class AnalyticsKafkaProducer extends AbstractKafkaProducer<UserMatching> {

    @Override
    @Outgoing("matching-routes-topic")
    public Publisher<String> getEventPublisher() {
        return super.getEventPublisher();
    }
}
