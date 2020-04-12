package com.r00ta.telematics.platform.enrich.messaging;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.r00ta.telematics.platform.enrich.IEnrichService;
import com.r00ta.telematics.platform.enrich.messaging.dto.CouldEvent;
import com.r00ta.telematics.platform.enrich.messaging.dto.TripCloudEvent;
import com.r00ta.telematics.platform.enrich.models.TripModel;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TripKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripKafkaConsumer.class);

    @Inject
    IEnrichService enrichService;

    @Incoming("enrichment-topic")
    public void onProcessInstanceEvent(CouldEvent event) {
        processEvent(event);
    }

    protected void processEvent(CouldEvent event) {
        LOGGER.info("Processing a new event");
        enrichService.processTrip(new TripModel(event.data.event));
    }
}
