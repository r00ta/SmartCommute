package com.r00ta.telematics.platform.enrich.messaging.outgoing;

import javax.enterprise.context.ApplicationScoped;

import com.r00ta.telematics.platform.enrich.messaging.outgoing.dto.EnrichedTripSummaryDto;
import com.r00ta.telematics.platform.messaging.AbstractKafkaProducer;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.reactivestreams.Publisher;

@ApplicationScoped
public class TripKafkaProducer extends AbstractKafkaProducer<EnrichedTripSummaryDto> {

    @Override
    @Outgoing("scoring-topic")
    public Publisher<String> getEventPublisher() {
        return super.getEventPublisher();
    }
}
