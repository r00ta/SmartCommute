package com.r00ta.telematics.platform.trips.storage;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.platform.IStorageManager;
import com.r00ta.telematics.platform.SmartQuery;
import com.r00ta.telematics.platform.operators.LongOperator;
import com.r00ta.telematics.platform.operators.StringOperator;
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
        return storageManager.create(summary.tripId, summary, TRIPHEADERSINDEX);
    }

    @Override
    public Optional<TripSummaryModel> getTripHeaderById(String userId, String tripId) {
        SmartQuery query = new SmartQuery().where("tripId", StringOperator.EQUALS, tripId);
        List<TripSummaryModel> summaryOpt = storageManager.search(query, TRIPHEADERSINDEX, TripSummaryModel.class);
        return summaryOpt.isEmpty() ? null : Optional.of(summaryOpt.get(0));
    }

    @Override
    public List<TripSummaryModel> getTripsHeadersByTimeRange(String userId, Long from, Long to) {
        SmartQuery query = new SmartQuery().where("userId", StringOperator.EQUALS, userId).where("startTimestamp", LongOperator.GTE, from).where("startTimestamp", LongOperator.LTE, to).limit(10000);
        return storageManager.search(query, TRIPHEADERSINDEX, TripSummaryModel.class);
    }

    @Override
    public boolean storeTrip(String userId, TripModel model) {
        return storageManager.create(model.tripId, model, TRIPINDEX);
    }

    @Override
    public Optional<TripModel> getTripById(String userId, String tripId) {
        SmartQuery query = new SmartQuery().where("tripId", StringOperator.EQUALS, tripId);
        List<TripModel> trips = storageManager.search(query, TRIPINDEX, TripModel.class);
        return trips.isEmpty() ? null : Optional.of(trips.get(0));
    }

    @Override
    public List<TripModel> getTripsByTimeRange(String userId, Long from, Long to) {
        SmartQuery query = new SmartQuery().where("userId", StringOperator.EQUALS, userId).where("startTimestamp", LongOperator.GTE, from).where("startTimestamp", LongOperator.LTE, to).limit(10000);
        return storageManager.search(query, TRIPINDEX, TripModel.class);
    }
}
