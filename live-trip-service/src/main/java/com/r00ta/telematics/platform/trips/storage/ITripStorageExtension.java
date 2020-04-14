package com.r00ta.telematics.platform.trips.storage;

import java.util.List;

import com.r00ta.telematics.platform.trips.models.TripModel;
import com.r00ta.telematics.platform.trips.models.TripSummaryModel;

public interface ITripStorageExtension {

    boolean storeTripHeader(String userId, TripSummaryModel summary);

    TripSummaryModel getTripHeaderById(String userId, String tripId);

    List<TripSummaryModel> getTripsHeadersByTimeRange(String userId, Long from, Long to);

    boolean storeTrip(String userId, TripModel model);

    TripModel getTripById(String userId, String tripId);

    List<TripModel> getTripsByTimeRange(String userId, Long from, Long to);
}
