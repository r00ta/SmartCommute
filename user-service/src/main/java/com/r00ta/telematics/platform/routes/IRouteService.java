package com.r00ta.telematics.platform.routes;

import java.time.DayOfWeek;
import java.util.List;

import com.r00ta.telematics.platform.routes.models.PassengerRideReference;
import com.r00ta.telematics.platform.routes.models.Route;

public interface IRouteService {

    Route getRouteById(String routeId);

    List<Route> getUserRoutes(String userId);

    boolean storeRoute(Route route);

    List<PassengerRideReference> getPassengers(String userId, String routeId, DayOfWeek day);

    boolean deletePassenger(String userId, String routeId, String passengerId, DayOfWeek[] days);

    boolean deleteDriver(String userId, String routeId, String driverId, DayOfWeek[] days);
}
