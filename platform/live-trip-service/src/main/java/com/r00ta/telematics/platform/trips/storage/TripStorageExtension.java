package com.r00ta.telematics.platform.trips.storage;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.platform.IStorageManager;
import com.r00ta.telematics.platform.trips.models.TripModel;
import com.r00ta.telematics.platform.trips.models.TripSummaryModel;

@ApplicationScoped
public class TripStorageExtension implements ITripStorageExtension {

    private static final String TRIPINDEX = "tripsindex";
    private static final String TRIPHEADERSINDEX = "tripsheadersindex";

    private static ObjectMapper objectMapper;

    @Inject
    IStorageManager storageManager;

    @PostConstruct
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public boolean storeTripHeader(String userId, TripSummaryModel summary) {
        try {
            storageManager.create(summary.tripId, objectMapper.writeValueAsString(summary), TRIPHEADERSINDEX);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public TripSummaryModel getTripHeaderById(String userId, String tripId) {
        String request = "{ \n" +
                "    \"query\": {\n" +
                "        \"match\": { \"tripId\" : \"" + tripId + "\"}\n" +
                "    }\n" +
                "}\n";
        return storageManager.search(request, TRIPHEADERSINDEX, TripSummaryModel.class).get(0);
    }

    @Override
    public List<TripSummaryModel> getTripsHeadersByTimeRange(String userId, Long from, Long to) {
        String request = String.format(
                "{\"size\": 10000, \"query\" : { \"bool\": {\n" +
                        "          \"must\": [{\"match\": { \"userId\" : \"" + userId + "\"} }," +
                        "{\"range\" : {\"startTimestamp\" : {\"gte\" : %s, \"lte\" : %s}}}" +
                        " ] } } }", from, to);
        return storageManager.search(request, TRIPHEADERSINDEX, TripSummaryModel.class);
    }

    @Override
    public boolean storeTrip(String userId, TripModel model) {
        try {
            storageManager.create(model.tripId, objectMapper.writeValueAsString(model), TRIPINDEX);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public TripModel getTripById(String userId, String tripId) {
        String request = "{ \n" +
                "    \"query\": {\n" +
                "        \"match\": { \"tripId\" : \"" + tripId + "\"}\n" +
                "    }\n" +
                "}\n";
        return storageManager.search(request, TRIPINDEX, TripModel.class).get(0);
    }

    @Override
    public List<TripModel> getTripsByTimeRange(String userId, Long from, Long to) {
        String request = String.format(
                "{\"size\": 10000, \"query\" : { \"bool\": {\n" +
                        "          \"must\": [{\"match\": { \"userId\" : \"" + userId + "\"} }," +
                        "{\"range\" : {\"startTimestamp\" : {\"gte\" : %d, \"lte\" : %d}}}" +
                        " ] } } }", from, to);
        return storageManager.search(request, TRIPINDEX, TripModel.class);
    }
}
