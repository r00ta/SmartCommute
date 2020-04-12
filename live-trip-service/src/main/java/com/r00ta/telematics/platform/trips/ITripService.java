package com.r00ta.telematics.platform.trips;

import java.util.Date;
import java.util.List;

import com.r00ta.telematics.platform.trips.models.TripModel;
import com.r00ta.telematics.platform.trips.models.TripSummaryModel;

public interface ITripService {

    TripSummaryModel getTripHeaderById(String userId, String tripId);

    List<TripSummaryModel> getTripsHeadersByTimeRange(String userId, Long from, Long to);

    boolean storeTrip(String userId, TripModel trip);

    TripModel getTrip(String userId, String tripId);

    List<TripModel> getTripsByTimeRange(String userId, Long from, Long to);


}
