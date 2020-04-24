package com.r00ta.telematics.platform.users.messaging.enriched;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.r00ta.telematics.platform.users.IUserService;
import com.r00ta.telematics.platform.users.messaging.enriched.dto.EnrichedCloudEvent;
import com.r00ta.telematics.platform.users.models.EnrichedTripSummary;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class EnrichedTripKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnrichedTripKafkaConsumer.class);

    @Inject
    IUserService userService;

    @Incoming("scoring-topic")
    public void onProcessInstanceEvent(EnrichedCloudEvent event) {
        processEvent(event);
    }

    protected void processEvent(EnrichedCloudEvent event) {
        LOGGER.info("Processing a new event");
        userService.processScore(new EnrichedTripSummary(event.data.event));
    }
}
