package com.r00ta.telematics.platform.mocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import com.r00ta.telematics.platform.routes.models.Route;
import com.r00ta.telematics.platform.routes.storage.IRoutesStorageExtension;
import io.quarkus.test.Mock;

public class RoutesStorageMock implements IRoutesStorageExtension {

    private Map<String, Route> storage = new HashMap<>();

    @Override
    public Optional<Route> getRouteById(String routeId){
        return Optional.of(storage.get(routeId));
    }

    @Override
    public List<Route> getUserRoutes(String userId){
        return storage.values().stream().filter(x -> x.userId.equals(userId)).collect(Collectors.toList());
    }

    @Override
    public boolean storeRoute(Route route){
        if (storage.containsKey(route.routeId)){
            storage.replace(route.routeId, route);
            return true;
        }
        storage.put(route.routeId, route);
        return true;
    }
}
