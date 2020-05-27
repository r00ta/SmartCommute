package com.r00ta.telematics.platform.routes;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import com.r00ta.telematics.platform.routes.models.DayRouteDrive;
import com.r00ta.telematics.platform.routes.models.MatchingPendingStatus;
import com.r00ta.telematics.platform.routes.models.PassengerRideReference;
import com.r00ta.telematics.platform.routes.models.PendingMatching;
import com.r00ta.telematics.platform.routes.models.Route;
import com.r00ta.telematics.platform.routes.models.RouteMatching;
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

    @Override
    public List<PendingMatching> getPendingMatchings(String userId) {
        return storageExtension.getPendingMatchings(userId);
    }

    /**
     * This function will retrieve the two maching documents: the one of the user and the one of the matched user. If the user would like
     * to decline the matching, it will delete both document (the matched user will simply don't see it anymore).
     * If the user would like to accept it, the document is updated and it checks if the matched user has already accepted it. If so, the routes of both users are updated.
     *
     * @param userId:     the user id
     * @param matchingId: the matching id
     * @param status:     the new status
     * @return
     */
    @Override
    public boolean updatePendingMatching(String userId, String matchingId, MatchingPendingStatus status) {

        List<PendingMatching> matchings = storageExtension.getPendingMatchingsById(matchingId);

        Optional<PendingMatching> userMatchingOpt = matchings.stream().filter(x -> x.userId.equals(userId)).findFirst();
        if (!userMatchingOpt.isPresent()) {
            LOGGER.warn(String.format("Could not find pending matching with ID %s during the update."), matchingId);
            return false;
        }

        PendingMatching userMatching = userMatchingOpt.get();

        Optional<PendingMatching> pairedMatchingOpt = matchings.stream().filter(x -> x.userId.equals(userMatching.matchedUserId)).findFirst();
        if (!pairedMatchingOpt.isPresent()) {
            LOGGER.warn(String.format("Could not find paired pending matching with ID %s during the update."), matchingId);
            return false;
        }

        PendingMatching pairedMatching = pairedMatchingOpt.get();

        if (status == MatchingPendingStatus.DECLINED || pairedMatching.status == MatchingPendingStatus.DECLINED) {
            storageExtension.deleteMatchings(matchingId);
            return true;
        }

        if (status == MatchingPendingStatus.ACCEPTED) {
            if (pairedMatching.status == MatchingPendingStatus.ACCEPTED) {
                userService.storeNews(userId, "You have been successfully paired with the user.");
                userService.storeNews(pairedMatching.matchedUserId, "The user X has been paired with you for your route");

                Route userRoute = getRouteById(userMatching.routeId).get();
                Route matchedRoute = getRouteById(userMatching.matchedRouteId).get();

                DayRouteDrive userRouteDrive = userRoute.dayRides.get(userMatching.day);
                DayRouteDrive matchedRouteDrive = matchedRoute.dayRides.get(userMatching.day);

                userRouteDrive.isADriverRide = userMatching.asDriver;
                userRouteDrive.isAPassengerRoute = userMatching.asPassenger;
                matchedRouteDrive.isADriverRide = pairedMatching.asDriver;
                matchedRouteDrive.isAPassengerRoute = pairedMatching.asPassenger;
                if (userMatching.asDriver) {
                    userRouteDrive.passengerReferences.add(new PassengerRideReference(userMatching.matchedUserId, userMatching.matchedRouteId, userMatching.matchedUserId)); // TODO: CHANGE INTO NAME
                    matchedRouteDrive.driverReference.driverRouteId = userMatching.routeId;
                    matchedRouteDrive.driverReference.driverUserId = userMatching.userId;
                    matchedRouteDrive.driverReference.driverName = userMatching.userId; // TODO: CHANGE INTO NAME
                } else {
                    matchedRouteDrive.passengerReferences.add(new PassengerRideReference(userMatching.userId, userMatching.routeId, userMatching.userId)); // TODO: CHANGE INTO NAME
                    userRouteDrive.driverReference.driverRouteId = userMatching.matchedRouteId;
                    userRouteDrive.driverReference.driverUserId = userMatching.matchedUserId;
                    userRouteDrive.driverReference.driverName = userMatching.matchedUserId; // TODO: CHANGE INTO NAME
                }
                storageExtension.updateRoute(userRoute);
                storageExtension.updateRoute(matchedRoute);
            } else {
                userMatching.status = status;
                storageExtension.updatePendingMatching(userMatching);
            }
        }

        return true;
    }

    @Override
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

        boolean driverSuccess = storageExtension.updateRoute(userRoute);
        boolean passengerSuccess = storageExtension.updateRoute(passengerRoute);

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

        boolean passengerSuccess = storageExtension.updateRoute(passengerRoute);

        boolean driverSuccess = storageExtension.updateRoute(driverRoute);

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

    @Override
    public void processMatching(RouteMatching matching) {
        String matchingId = UUID.randomUUID().toString();
        PendingMatching driverPendingMatching = new PendingMatching(matchingId, matching.driverUserId, matching.driverRouteId, matching.day, false, MatchingPendingStatus.PENDING, matching.startLocationPickUp, matching.endLocationDropOff, matching.passengerUserId, matching.passengerRouteId);
        PendingMatching passengerPendingMatching = new PendingMatching(matchingId, matching.passengerUserId, matching.passengerRouteId, matching.day, true, MatchingPendingStatus.PENDING, matching.startLocationPickUp, matching.endLocationDropOff, matching.driverUserId, matching.driverRouteId);
        storageExtension.storePendingMatching(driverPendingMatching);
        storageExtension.storePendingMatching(passengerPendingMatching);
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