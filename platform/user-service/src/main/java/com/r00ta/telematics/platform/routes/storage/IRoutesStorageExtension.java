package com.r00ta.telematics.platform.routes.storage;

import java.util.List;

import com.r00ta.telematics.platform.routes.models.Route;

public interface IRoutesStorageExtension {

    Route getRouteById(String routeId);

    List<Route> getUserRoutes(String userId);

    boolean storeRoute(Route route);

    //    boolean storeEnrichedTrip(EnrichedTrip trip);
//
//    EnrichedTrip getTripById(String tripId);
//
//    List<EnrichedTrip> getTripsByTimeRange(String userId, Long from, Long to);
}
