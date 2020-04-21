package com.r00ta.telematics.platform.enrich.messaging.incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.r00ta.telematics.platform.enrich.IEnrichService;
import com.r00ta.telematics.platform.enrich.messaging.incoming.dto.CloudEvent;
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
    public void onProcessInstanceEvent(CloudEvent event) {
        processEvent(event);
    }

    protected void processEvent(CloudEvent event) {
        LOGGER.info("Processing a new event");
        enrichService.processTrip(new TripModel(event.data.event));
    }
}
