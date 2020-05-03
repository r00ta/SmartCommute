package com.r00ta.telematics.platform.enrich.storage;

import java.util.List;
import java.util.Optional;

import com.r00ta.telematics.platform.enrich.models.EnrichedTrip;
import com.r00ta.telematics.platform.enrich.models.EnrichedTripHeader;

public interface IEnrichStorageExtension {

    boolean storeEnrichedTrip(EnrichedTrip trip);

    Optional<EnrichedTrip> getTripById(String tripId);

    List<EnrichedTripHeader> getTripsHeadersByTimeRange(String userId, Long from, Long to);
}
