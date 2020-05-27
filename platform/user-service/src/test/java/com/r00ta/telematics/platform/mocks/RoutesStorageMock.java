package com.r00ta.telematics.platform.mocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.r00ta.telematics.platform.routes.models.PendingMatching;
import com.r00ta.telematics.platform.routes.models.Route;
import com.r00ta.telematics.platform.routes.storage.IRoutesStorageExtension;

public class RoutesStorageMock implements IRoutesStorageExtension {

    private Map<String, Route> storageRoutes = new HashMap<>();
    private Map<String, PendingMatching> storagePendingMatchings = new HashMap<>();

    @Override
    public Optional<Route> getRouteById(String routeId) {
        return Optional.of(storageRoutes.get(routeId));
    }

    @Override
    public List<Route> getUserRoutes(String userId) {
        return storageRoutes.values().stream().filter(x -> x.userId.equals(userId)).collect(Collectors.toList());
    }

    @Override
    public boolean storeRoute(Route route) {
        if (storageRoutes.containsKey(route.routeId)) {
            storageRoutes.replace(route.routeId, route);
            return true;
        }
        storageRoutes.put(route.routeId, route);
        return true;
    }

    @Override
    public boolean updateRoute(Route route) {
        return storeRoute(route);
    }

    @Override
    public List<PendingMatching> getPendingMatchings(String userId) {
        return storagePendingMatchings.values().stream().filter(x -> x.userId.equals(userId)).collect(Collectors.toList());
    }

    @Override
    public boolean storePendingMatching(PendingMatching matching) {
        if (storagePendingMatchings.containsKey(matching.matchingId + matching.userId)) {
            storagePendingMatchings.replace(matching.matchingId + matching.userId, matching);
            return true;
        }
        storagePendingMatchings.put(matching.matchingId + matching.userId, matching);
        return true;
    }

    @Override
    public boolean updatePendingMatching(PendingMatching matching) {
        return storePendingMatching(matching);
    }

    @Override
    public Optional<PendingMatching> getPendingMatching(String userId, String matchingId) {
        return storagePendingMatchings.values().stream().filter(x -> x.userId.equals(userId) && x.matchingId.equals(matchingId)).findFirst();
    }

    @Override
    public List<PendingMatching> getPendingMatchingsById(String matchingId) {
        return storagePendingMatchings.values().stream().filter(x -> x.matchingId.equals(matchingId)).collect(Collectors.toList());
    }

    @Override
    public boolean deleteMatchings(String matchingId) {
        List<PendingMatching> pendings = getPendingMatchingsById(matchingId);
        pendings.forEach(x -> storagePendingMatchings.remove(x));
        return true;
    }
}
