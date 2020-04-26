package com.r00ta.telematics.platform.routes;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import com.r00ta.telematics.platform.routes.models.DayRouteDrive;
import com.r00ta.telematics.platform.routes.models.PassengerRideReference;
import com.r00ta.telematics.platform.routes.models.Route;
import com.r00ta.telematics.platform.routes.storage.IRoutesStorageExtension;
import com.r00ta.telematics.platform.users.IUserService;
import com.r00ta.telematics.platform.users.models.User;
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
    public Optional<Route> getRouteById(String routeId) {
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
        Optional<Route> userRoute = getRouteById(routeId);
        if (!userRoute.isPresent()) {
            throw new NotFoundException("Route not found.");
        }

        LOGGER.info(String.valueOf(userRoute.get().dayRides.get(day)));
        return userRoute.get().dayRides.get(day).passengerReferences;
    }

    @Override
    public boolean deletePassenger(String userId, String routeId, String passengerId, DayOfWeek[] days) {
        Optional<Route> userRouteOpt = getRouteById(routeId);

        if (!userRouteOpt.isPresent()) {
            LOGGER.warn("Route not found " + routeId);
            return false;
        }
        Route userRoute = userRouteOpt.get();

        String passengerRouteId = userRoute.findPassengerRouteId(passengerId);

        removePassengerFromDriverRoute(userRoute, passengerId, days); // delete from all days

        Optional<Route> passengerRouteOpt = getRouteById(passengerRouteId);
        if (!passengerRouteOpt.isPresent()) {
            LOGGER.warn("Route not found " + passengerRouteId);
            return false;
        }
        Route passengerRoute = passengerRouteOpt.get();

        removeDriverFromPassengerRoute(passengerRoute, userRoute.userId, days); // delete from all days

        boolean driverSuccess = storeRoute(userRoute);
        boolean passengerSuccess = storeRoute(passengerRoute);

        if (!driverSuccess || !passengerSuccess) {
            LOGGER.error("something went wrong when trying to update driver and passenger relation - delete passenger");
            return false;
        }

        // Notify the other user of the change
        Optional<User> userOpt = userService.getUserById(userId);
        if (!userOpt.isPresent()) {
            return false;
        }

        return userService.storeNews(passengerId, String.format("%s has just removed you from his routes. Your routes have been adjusted accordingly.", userOpt.get().name));
    }

    @Override
    public boolean deleteDriver(String userId, String routeId, String driverId, DayOfWeek[] days) {
        Optional<Route> passengerRouteOpt = getRouteById(routeId);
        if (!passengerRouteOpt.isPresent()) {
            LOGGER.warn("Route not found " + routeId);
            return false;
        }
        Route passengerRoute = passengerRouteOpt.get();

        String driverRouteId = passengerRoute.findDriverRouteId(driverId);

        removeDriverFromPassengerRoute(passengerRoute, driverId, days);

        Optional<Route> driverRouteOpt = getRouteById(driverRouteId);
        if (!driverRouteOpt.isPresent()) {
            LOGGER.warn("Route not found " + driverRouteId);
            return false;
        }
        Route driverRoute = driverRouteOpt.get();

        removePassengerFromDriverRoute(driverRoute, userId, days); // delete from all days

        boolean passengerSuccess = storeRoute(passengerRoute);

        boolean driverSuccess = storeRoute(driverRoute);

        if (!driverSuccess || !passengerSuccess) {
            LOGGER.error("something went wrong when trying to update driver and passenger relation - delete passenger");
            return false;
        }

        // Notify the other user of the change
        Optional<User> userOpt = userService.getUserById(userId);
        if (!userOpt.isPresent()) {
            return false;
        }

        userService.storeNews(driverId, String.format("%s has just removed you from his routes. Your routes have been adjusted accordingly.", userOpt.get().name));

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