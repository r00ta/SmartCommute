package com.r00ta.telematics.platform.routes.storage;

import java.util.List;
import java.util.Optional;

import com.r00ta.telematics.platform.routes.models.PendingMatching;
import com.r00ta.telematics.platform.routes.models.Route;

public interface IRoutesStorageExtension {

    Optional<Route> getRouteById(String routeId);

    List<Route> getUserRoutes(String userId);

    boolean storeRoute(Route route);

    boolean updateRoute(Route route);

    List<PendingMatching> getPendingMatchings(String userId);

    boolean storePendingMatching(PendingMatching matching);

    boolean updatePendingMatching(PendingMatching matching);

    Optional<PendingMatching> getPendingMatching(String userId, String matchingId);

    List<PendingMatching> getPendingMatchingsById(String matchingId);

    boolean deleteMatchings(String matchingId);
}
