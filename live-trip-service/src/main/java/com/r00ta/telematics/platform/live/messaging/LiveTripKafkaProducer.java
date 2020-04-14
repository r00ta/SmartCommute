package com.r00ta.telematics.platform.live.messaging;

import javax.enterprise.context.ApplicationScoped;

import com.r00ta.telematics.platform.live.messaging.dto.LiveModelDto;
import com.r00ta.telematics.platform.messaging.AbstractKafkaProducer;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.reactivestreams.Publisher;

@ApplicationScoped
public class LiveTripKafkaProducer extends AbstractKafkaProducer<LiveModelDto> {

    @Override
    @Outgoing("live-topic")
    public Publisher<String> getEventPublisher() {
        return super.getEventPublisher();
    }
}
