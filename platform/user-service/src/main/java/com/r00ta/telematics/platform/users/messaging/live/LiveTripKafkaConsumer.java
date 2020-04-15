package com.r00ta.telematics.platform.users.messaging.live;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.r00ta.telematics.platform.users.IUserService;
import com.r00ta.telematics.platform.users.messaging.live.dto.LiveCloudEvent;
import com.r00ta.telematics.platform.users.models.LiveTrip;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class LiveTripKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveTripKafkaConsumer.class);

    @Inject
    IUserService userService;

    @Incoming("live-topic")
    public void onProcessInstanceEvent(LiveCloudEvent event) {
        processEvent(event);
    }

    protected void processEvent(LiveCloudEvent event) {
        LOGGER.info("Processing a new live trip event");
        userService.notifyLiveTrip(new LiveTrip(event.data.event));
    }
}
