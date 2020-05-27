package com.r00ta.telematics.platform.routes.storage;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.platform.IStorageManager;
import com.r00ta.telematics.platform.SmartQuery;
import com.r00ta.telematics.platform.operators.StringOperator;
import com.r00ta.telematics.platform.routes.models.PendingMatching;
import com.r00ta.telematics.platform.routes.models.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: check application scoped? Request scoped?
@ApplicationScoped
public class RoutesStorageExtension implements IRoutesStorageExtension {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoutesStorageExtension.class);

    private static final String ROUTES_INDEX = "routesindex";
    private static final String MATCHINGS_INDEX = "matchingsindex";
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
        storageManager.create(route.routeId, route, ROUTES_INDEX);
        return true;
    }

    @Override
    public boolean updateRoute(Route route) {
        return storageManager.update(new SmartQuery().where("routeId", StringOperator.EQUALS, route.routeId), route, ROUTES_INDEX);
    }

    @Override
    public boolean storePendingMatching(PendingMatching matching) {
        return storageManager.create(matching.matchingId, matching, MATCHINGS_INDEX);
    }

    @Override
    public boolean updatePendingMatching(PendingMatching matching) {
        return storageManager.update(new SmartQuery().where("matchingId", StringOperator.EQUALS, matching.matchingId), matching, MATCHINGS_INDEX);
    }

    public Optional<PendingMatching> getPendingMatching(String userId, String matchingId) {
        List<PendingMatching> matchings = storageManager.search(new SmartQuery().where("matchingId", StringOperator.EQUALS, matchingId).where("userId", StringOperator.EQUALS, userId), MATCHINGS_INDEX, PendingMatching.class);
        return matchings.isEmpty() ? null : Optional.of(matchings.get(0));
    }

    @Override
    public List<PendingMatching> getPendingMatchingsById(String matchingId) {
        return storageManager.search(new SmartQuery().where("matchingId", StringOperator.EQUALS, matchingId), MATCHINGS_INDEX, PendingMatching.class);
    }

    @Override
    public List<PendingMatching> getPendingMatchings(String userId) {
        return storageManager.search(new SmartQuery().where("userId", StringOperator.EQUALS, userId), MATCHINGS_INDEX, PendingMatching.class);
    }

    @Override
    public boolean deleteMatchings(String matchingId) {
        return storageManager.delete(new SmartQuery().where("matchingId", StringOperator.EQUALS, matchingId), MATCHINGS_INDEX, PendingMatching.class);
    }
}