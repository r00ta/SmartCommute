package com.r00ta.telematics.platform.here;

import java.util.Map;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import static java.util.Collections.singletonMap;

public class KafkaTestResource implements QuarkusTestResourceLifecycleManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTestResource.class);
    public static final String KAFKA_BOOTSTRAP_SERVERS = "kafka.bootstrap.servers";
    private static KafkaContainer KAFKA = new KafkaContainer().withLogConsumer(new Slf4jLogConsumer(LOGGER));

    @Override
    public Map<String, String> start() {
        if (KAFKA.isRunning() == false) {
            KAFKA.start();
            LOGGER.info("Kafka servers: {}", KAFKA.getBootstrapServers());
        }
        return singletonMap(KAFKA_BOOTSTRAP_SERVERS, KAFKA.getBootstrapServers());
    }

    @Override
    public void stop() {
        KAFKA.stop();
    }
}

