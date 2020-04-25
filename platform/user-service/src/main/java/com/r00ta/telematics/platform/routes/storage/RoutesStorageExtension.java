package com.r00ta.telematics.platform.routes.storage;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.platform.IStorageManager;
import com.r00ta.telematics.platform.SmartQuery;
import com.r00ta.telematics.platform.operators.StringOperator;
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
    public Optional<Route> getRouteById(String routeId) {
        SmartQuery query = new SmartQuery().where("routeId", StringOperator.EQUALS, routeId);
        List<Route> routes = storageManager.search(query, ROUTES_INDEX, Route.class);
        return routes.isEmpty() ? null : Optional.of(routes.get(0));
    }

    @Override
    public List<Route> getUserRoutes(String userId) {
        SmartQuery query = new SmartQuery().where("userId", StringOperator.EQUALS, userId);
        return storageManager.search(query, ROUTES_INDEX, Route.class);
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