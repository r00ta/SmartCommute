package com.r00ta.telematics.platform.messaging;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.r00ta.telematics.platform.messaging.model.CloudEventDto;
import io.cloudevents.v1.CloudEventBuilder;
import io.cloudevents.v1.CloudEventImpl;
import io.reactivex.BackpressureStrategy;
import io.reactivex.subjects.PublishSubject;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractKafkaProducer<T extends CloudEventDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractKafkaProducer.class);

    private final PublishSubject<String> eventSubject = PublishSubject.create();

    private static String urlEncode(String input) {
        try {
            return URLEncoder.encode(input, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendEventAsync(T event) {
        CompletableFuture.runAsync(() -> {
            handleEvent(event);
        });
    }

    public String handleEvent(T event) {
        try {
            LOGGER.info("Sending new event");

            Map<String, Object> dataMap = new HashMap<>();

            dataMap.put("event", event);

            CloudEventImpl<Map<String, Object>> cloudEvent =
                    CloudEventBuilder.<Map<String, Object>>builder()
                            .withType("String")
                            .withId(UUID.randomUUID().toString())
                            .withSource(URI.create(String.format("%s/%s",
                                                                 event.getEventProducer(),
                                                                 event.getEventId())
                            ))
                            .withData(dataMap)
                            .build();

            String payload = io.cloudevents.json.Json.encode(cloudEvent);

            LOGGER.info("Payload: {}", payload);

            eventSubject.onNext(payload);
            return cloudEvent.getAttributes().getId();
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public Publisher<String> getEventPublisher() {
        return eventSubject.toFlowable(BackpressureStrategy.BUFFER);
    }
}
