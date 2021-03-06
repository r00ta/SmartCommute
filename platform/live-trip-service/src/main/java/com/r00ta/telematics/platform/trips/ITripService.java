package com.r00ta.telematics.platform.trips;

import java.util.List;
import java.util.Optional;

import com.r00ta.telematics.platform.trips.models.TripModel;
import com.r00ta.telematics.platform.trips.models.TripSummaryModel;

public interface ITripService {

    Optional<TripSummaryModel> getTripHeaderById(String userId, String tripId);

    List<TripSummaryModel> getTripsHeadersByTimeRange(String userId, Long from, Long to);

    boolean storeAndSendTripAsync(String userId, TripModel trip);

    Optional<TripModel> getTrip(String userId, String tripId);

    List<TripModel> getTripsByTimeRange(String userId, Long from, Long to);
}
