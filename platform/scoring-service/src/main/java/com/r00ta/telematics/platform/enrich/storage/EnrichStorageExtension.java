package com.r00ta.telematics.platform.enrich.storage;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.platform.IStorageManager;
import com.r00ta.telematics.platform.SmartQuery;
import com.r00ta.telematics.platform.enrich.models.EnrichedTrip;
import com.r00ta.telematics.platform.operators.LongOperator;
import com.r00ta.telematics.platform.operators.StringOperator;

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
    public Optional<EnrichedTrip> getTripById(String tripId) {
        SmartQuery query = new SmartQuery().where("tripId", StringOperator.EQUALS, tripId);
        List<EnrichedTrip> trip = storageManager.search(query, ENRICHED_TRIP_INDEX, EnrichedTrip.class);
        return trip.isEmpty() ? null : Optional.of(trip.get(0));
    }

    @Override
    public List<EnrichedTrip> getTripsByTimeRange(String userId, Long from, Long to) {
        SmartQuery query = new SmartQuery().where("userId", StringOperator.EQUALS, userId).where("startTimestamp", LongOperator.GTE, from).where("startTimestamp", LongOperator.LTE, to).limit(10000);
        return storageManager.search(query, ENRICHED_TRIP_INDEX, EnrichedTrip.class);
    }
}