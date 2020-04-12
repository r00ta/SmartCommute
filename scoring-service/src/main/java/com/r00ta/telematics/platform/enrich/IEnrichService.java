package com.r00ta.telematics.platform.enrich;

import java.util.List;

import com.r00ta.telematics.platform.enrich.models.EnrichedTrip;
import com.r00ta.telematics.platform.enrich.models.TripModel;

public interface IEnrichService {

    void processTrip(TripModel trip);

    boolean storeTrip(String userId, TripModel trip);

    EnrichedTrip getTrip(String userId, String tripId);

    List<EnrichedTrip> getTripsByTimeRange(String userId, Long from, Long to);
}
