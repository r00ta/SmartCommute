package com.r00ta.telematics.platform.messaging.incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.r00ta.telematics.platform.IAnalyticsService;
import com.r00ta.telematics.platform.messaging.incoming.dto.CloudEvent;
import com.r00ta.telematics.platform.models.AnalyticsRoute;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class RouteKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteKafkaConsumer.class);

    @Inject
    IAnalyticsService analyticsService;

    @Incoming("route-analytics-topic")
    public void onProcessInstanceEvent(CloudEvent event) {
        processEvent(event);
    }

    protected void processEvent(CloudEvent event) {
        LOGGER.info("Processing a new event");
        analyticsService.processRoute(AnalyticsRoute.from(event.data.event));
    }
}
