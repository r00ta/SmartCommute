package com.r00ta.telematics.platform.enrich.storage;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.platform.database.IStorageManager;
import com.r00ta.telematics.platform.enrich.models.EnrichedTrip;

// TODO: check application scoped? Request scoped?
@ApplicationScoped
public class EnrichStorageExtension implements IEnrichStorageExtension {

    private static final String ENRICHED_TRIP_INDEX = "enrichedtripindex";
    private static ObjectMapper objectMapper;

    @Inject
    IStorageManager storageManager;

    @PostConstruct
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public boolean storeEnrichedTrip(EnrichedTrip trip) {
        try {
            storageManager.create(trip.tripId, objectMapper.writeValueAsString(trip), ENRICHED_TRIP_INDEX);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public EnrichedTrip getTripById(String tripId) {
        String request = "{ \n" +
                "    \"query\": {\n" +
                "        \"match\": { \"tripId\" : \"" + tripId + "\"}\n" +
                "    }\n" +
                "}\n";
        return storageManager.search(request, ENRICHED_TRIP_INDEX, EnrichedTrip.class).get(0);
    }

    @Override
    public List<EnrichedTrip> getTripsByTimeRange(String userId, Long from, Long to) {
        String request = String.format(
                "{\"size\": 10000, \"query\" : { \"bool\": {\n" +
                        "          \"must\": [{\"match\": { \"userId\" : \"" + userId + "\"} }," +
                        "{\"range\" : {\"startTimestamp\" : {\"gte\" : %d, \"lte\" : %d}}}" +
                        " ] } } }", from, to);
        return storageManager.search(request, ENRICHED_TRIP_INDEX, EnrichedTrip.class);
    }
}