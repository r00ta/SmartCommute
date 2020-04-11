package com.r00ta.telematics.platform.trips.messaging;

import javax.enterprise.context.ApplicationScoped;

import com.r00ta.telematics.platform.messaging.AbstractKafkaProducer;
import com.r00ta.telematics.platform.trips.messaging.dto.TripModelDto;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.reactivestreams.Publisher;

@ApplicationScoped
public class TripKafkaProducer extends AbstractKafkaProducer<TripModelDto> {

    @Override
    @Outgoing("enrichment-topic")
    public Publisher<String> getEventPublisher() {
        System.out.println("DSOAIJDIOASJDIOASJIODASJIODJAS");
        System.out.println("DSOAIJDIOASJDIOASJIODASJIODJAS");
        System.out.println("DSOAIJDIOASJDIOASJIODASJIODJAS");
        System.out.println("DSOAIJDIOASJDIOASJIODASJIODJAS");
        System.out.println("DSOAIJDIOASJDIOASJIODASJIODJAS");
        System.out.println("DSOAIJDIOASJDIOASJIODASJIODJAS");
        return super.getEventPublisher();
    }
}
