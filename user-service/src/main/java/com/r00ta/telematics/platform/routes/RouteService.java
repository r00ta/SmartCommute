package com.r00ta.telematics.platform.routes;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.r00ta.telematics.platform.routes.models.DayRouteDrive;
import com.r00ta.telematics.platform.routes.models.PassengerRideReference;
import com.r00ta.telematics.platform.routes.models.Route;
import com.r00ta.telematics.platform.routes.storage.IRoutesStorageExtension;
import com.r00ta.telematics.platform.users.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class RouteService implements IRouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteService.class);

    @Inject
    IRoutesStorageExtension storageExtension;

    @Inject
    IUserService userService;

    @Override
    public Route getRouteById(String routeId) {
        return storageExtension.getRouteById(routeId);
    }

    @Override
    public List<Route> getUserRoutes(String userId) {
        return storageExtension.getUserRoutes(userId);
    }

    @Override
    public boolean storeRoute(Route route) {
        return storageExtension.storeRoute(route);
    }

    public List<PassengerRideReference> getPassengers(String userId, String routeId, DayOfWeek day) {
        Route userRoute = getRouteById(routeId);
        return userRoute.dayRides.get(day).passengerReferences;
    }

    @Override
    public boolean deletePassenger(String userId, String routeId, String passengerId, DayOfWeek[] days) {
        Route userRoute = getRouteById(routeId);

        String passengerRouteId = userRoute.findPassengerRouteId(passengerId);

        removePassengerFromDriverRoute(userRoute, passengerId, days); // delete from all days

        Route passengerRoute = getRouteById(passengerRouteId);

        removeDriverFromPassengerRoute(passengerRoute, userRoute.userId, days); // delete from all days

        boolean driverSuccess = storeRoute(userRoute);
        boolean passengerSuccess = storeRoute(passengerRoute);

        if (!driverSuccess || !passengerSuccess) {
            LOGGER.error("something went wrong when trying to update driver and passenger relation - delete passenger");
            return false;
        }

        // Notify the other user of the change
        userService.storeNews(passengerId, String.format("%s has just removed you from his routes. Your routes have been adjusted accordingly.", userService.getUserById(userId).name));

        return true;
    }

    @Override
    public boolean deleteDriver(String userId, String routeId, String driverId, DayOfWeek[] days) {
        Route passengerRoute = getRouteById(routeId);

        String driverRouteId = passengerRoute.findDriverRouteId(driverId);

        removeDriverFromPassengerRoute(passengerRoute, driverId, days);

        Route driverRoute = getRouteById(driverRouteId);

        removePassengerFromDriverRoute(driverRoute, userId, days); // delete from all days

        boolean passengerSuccess = storeRoute(passengerRoute);

        boolean driverSuccess = storeRoute(driverRoute);

        if (!driverSuccess || !passengerSuccess) {
            LOGGER.error("something went wrong when trying to update driver and passenger relation - delete passenger");
            return false;
        }

        // Notify the other user of the change
        userService.storeNews(driverId, String.format("%s has just removed you from his routes. Your routes have been adjusted accordingly.", userService.getUserById(userId).name));

        return true;
    }

    private void removePassengerFromDriverRoute(Route route, String passengerId, DayOfWeek[] days) {
        for (DayOfWeek day : days) {
            if (route.dayRides.containsKey(day)) {
                DayRouteDrive dayRide = route.dayRides.get(day);
                Optional<PassengerRideReference> first = dayRide.passengerReferences.stream().filter(x -> x.passengerUserId.equals(passengerId)).findFirst();
                if (first.isPresent()) {
                    PassengerRideReference rideReference = first.get();
                    dayRide.passengerReferences.remove(rideReference);
                }
            }
        }
    }

    private void removeDriverFromPassengerRoute(Route route, String driverUserId, DayOfWeek[] days) {
        for (DayOfWeek day : days) {
            if (route.dayRides.containsKey(day)) {
                DayRouteDrive dayRide = route.dayRides.get(day);
                if (dayRide.driverReference.driverUserId.equals(driverUserId)) {
                    route.dayRides.replace(day, DayRouteDrive.createDefault());
                }
            }
        }
    }
}