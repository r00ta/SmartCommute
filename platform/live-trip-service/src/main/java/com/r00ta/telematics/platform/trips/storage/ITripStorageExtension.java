package com.r00ta.telematics.platform.trips.storage;

import java.util.List;
import java.util.Optional;

import com.r00ta.telematics.platform.trips.models.TripModel;
import com.r00ta.telematics.platform.trips.models.TripSummaryModel;

public interface ITripStorageExtension {

    boolean storeTripHeader(String userId, TripSummaryModel summary);

    Optional<TripSummaryModel> getTripHeaderById(String userId, String tripId);

    List<TripSummaryModel> getTripsHeadersByTimeRange(String userId, Long from, Long to);

    boolean storeTrip(String userId, TripModel model);

    Optional<TripModel> getTripById(String userId, String tripId);

    List<TripModel> getTripsByTimeRange(String userId, Long from, Long to);
}
