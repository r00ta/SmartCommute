package com.r00ta.telematics.platform.routes.messaging;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.r00ta.telematics.platform.routes.IRouteService;
import com.r00ta.telematics.platform.routes.messaging.dto.CloudEvent;
import com.r00ta.telematics.platform.routes.models.RouteMatching;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TripKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripKafkaConsumer.class);

    @Inject
    IRouteService routeService;

    @Incoming("matching-topic")
    public void onProcessInstanceEvent(CloudEvent event) {
        processEvent(event);
    }

    protected void processEvent(CloudEvent event) {
        LOGGER.info("Processing a new event");
        routeService.processMatching(RouteMatching.fromEvent(event.data.event));
    }
}
