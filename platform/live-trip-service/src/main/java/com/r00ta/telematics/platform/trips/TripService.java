package com.r00ta.telematics.platform.trips;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.r00ta.telematics.platform.trips.messaging.TripKafkaProducer;
import com.r00ta.telematics.platform.trips.messaging.dto.TripModelDto;
import com.r00ta.telematics.platform.trips.models.GpsLocation;
import com.r00ta.telematics.platform.trips.models.TripModel;
import com.r00ta.telematics.platform.trips.models.TripSummaryModel;
import com.r00ta.telematics.platform.trips.storage.ITripStorageExtension;

@ApplicationScoped
public class TripService implements ITripService {

    @Inject
    ITripStorageExtension storageManager;

    @Inject
    TripKafkaProducer kafkaProducer;

    @Override
    public TripSummaryModel getTripHeaderById(String userId, String tripId) {
        return storageManager.getTripHeaderById(userId, tripId);
    }

    @Override
    public List<TripSummaryModel> getTripsHeadersByTimeRange(String userId, Long from, Long to) {
        return storageManager.getTripsHeadersByTimeRange(userId, from, to);
    }

    @Override
    public boolean storeAndSendTripAsync(String userId, TripModel trip) {
        if (trip.positions == null || trip.positions.size() == 0){ // not going to send an empty trip in the platform
            return false;
        }

        GpsLocation lastLocation = trip.positions.get(trip.positions.size() - 1);
        TripSummaryModel summary = new TripSummaryModel(userId, trip.tripId, trip.positions.get(0), lastLocation, trip.startTimestamp, lastLocation.timestamp, lastLocation.timestamp - trip.positions.get(0).timestamp);
        storageManager.storeTripHeader(userId, summary);
        kafkaProducer.sendEventAsync(new TripModelDto(trip));
        return storageManager.storeTrip(userId, trip);
    }

    @Override
    public TripModel getTrip(String userId, String tripId) {
        return storageManager.getTripById(userId, tripId);
    }

    @Override
    public List<TripModel> getTripsByTimeRange(String userId, Long from, Long to) {
        return storageManager.getTripsByTimeRange(userId, from, to);
    }
}
