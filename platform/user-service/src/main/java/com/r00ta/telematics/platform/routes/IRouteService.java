package com.r00ta.telematics.platform.routes;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import com.r00ta.telematics.platform.routes.models.MatchingPendingStatus;
import com.r00ta.telematics.platform.routes.models.PassengerRideReference;
import com.r00ta.telematics.platform.routes.models.PendingMatching;
import com.r00ta.telematics.platform.routes.models.Route;
import com.r00ta.telematics.platform.routes.models.RouteMatching;

public interface IRouteService {

    Optional<Route> getRouteById(String routeId);

    List<Route> getUserRoutes(String userId);

    boolean storeRoute(Route route);

    List<PassengerRideReference> getPassengers(String userId, String routeId, DayOfWeek day);

    boolean deletePassenger(String userId, String routeId, String passengerId, DayOfWeek[] days);

    boolean deleteDriver(String userId, String routeId, String driverId, DayOfWeek[] days);

    List<PendingMatching> getPendingMatchings(String userId);

    boolean updatePendingMatching(String userId, String matchingId, MatchingPendingStatus status);

    void processMatching(RouteMatching matching);
}
