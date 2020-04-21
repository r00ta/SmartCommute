package com.r00ta.telematics.platform.enrich.messaging.outgoing;

import javax.enterprise.context.ApplicationScoped;

import com.r00ta.telematics.platform.enrich.messaging.outgoing.dto.EnrichedTripSummaryDto;
import com.r00ta.telematics.platform.enrich.messaging.outgoing.dto.RouteAnalyticsDto;
import com.r00ta.telematics.platform.messaging.AbstractKafkaProducer;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.reactivestreams.Publisher;

@ApplicationScoped
public class RouteAnalyticsKafkaProducer extends AbstractKafkaProducer<RouteAnalyticsDto> {

    @Override
    @Outgoing("route-analytics-topic")
    public Publisher<String> getEventPublisher() {
        return super.getEventPublisher();
    }
}
