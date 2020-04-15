package com.r00ta.telematics.platform.enrich.storage;

import java.util.List;

import com.r00ta.telematics.platform.enrich.models.EnrichedTrip;

public interface IEnrichStorageExtension {

    boolean storeEnrichedTrip(EnrichedTrip trip);

    EnrichedTrip getTripById(String tripId);

    List<EnrichedTrip> getTripsByTimeRange(String userId, Long from, Long to);
}
