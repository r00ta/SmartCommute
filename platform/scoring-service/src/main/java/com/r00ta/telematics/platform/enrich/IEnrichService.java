package com.r00ta.telematics.platform.enrich;

import java.util.List;
import java.util.Optional;

import com.r00ta.telematics.platform.enrich.models.EnrichedTrip;
import com.r00ta.telematics.platform.enrich.models.TripModel;

public interface IEnrichService {

    EnrichedTrip processTrip(TripModel trip);

    EnrichedTrip storeTrip(String userId, TripModel trip);

    Optional<EnrichedTrip> getTrip(String userId, String tripId);

    List<EnrichedTrip> getTripsByTimeRange(String userId, Long from, Long to);
}
