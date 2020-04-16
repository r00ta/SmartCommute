package com.r00ta.telematics.platform.routes.storage;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.platform.IStorageManager;
import com.r00ta.telematics.platform.routes.models.Route;

// TODO: check application scoped? Request scoped?
@ApplicationScoped
public class RoutesStorageExtension implements IRoutesStorageExtension {

    private static final String ROUTES_INDEX = "routesindex";
    private static ObjectMapper objectMapper;

    @Inject
    IStorageManager storageManager;

    @PostConstruct
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public Route getRouteById(String routeId) {
        String request = "{ \n" +
                "    \"query\": {\n" +
                "        \"match\": { \"routeId\" : \"" + routeId + "\"}\n" +
                "    }\n" +
                "}\n";
        return storageManager.search(request, ROUTES_INDEX, Route.class).get(0);
    }

    @Override
    public List<Route> getUserRoutes(String userId) {
        String request = "{ \n" +
                "    \"query\": {\n" +
                "        \"match\": { \"userId\" : \"" + userId + "\"}\n" +
                "    }\n" +
                "}\n";
        return storageManager.search(request, ROUTES_INDEX, Route.class);
    }

    @Override
    public boolean storeRoute(Route route) {
        try {
            storageManager.create(route.routeId, objectMapper.writeValueAsString(route), ROUTES_INDEX);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}