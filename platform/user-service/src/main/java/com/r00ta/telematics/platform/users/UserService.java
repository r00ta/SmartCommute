package com.r00ta.telematics.platform.users;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.r00ta.telematics.platform.routes.IRouteService;
import com.r00ta.telematics.platform.routes.models.PassengerRideReference;
import com.r00ta.telematics.platform.users.models.EnrichedTripSummary;
import com.r00ta.telematics.platform.users.models.LiveTrip;
import com.r00ta.telematics.platform.users.models.User;
import com.r00ta.telematics.platform.users.models.UserStatistics;
import com.r00ta.telematics.platform.users.storage.IUsersStorageExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class UserService implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Inject
    IUsersStorageExtension storageExtension;

    @Inject
    IRouteService routeService;

    @Override
    public Optional<User> getUserById(String user) {
        return storageExtension.getUserById(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return storageExtension.getUserByEmail(email);
    }

    @Override
    public boolean createUser(User user) {
        LOGGER.info("New create user process started: userId = " + user.userId);
        UserStatistics userStatisticsDocument = new UserStatistics(user.userId);
        boolean success = storeUserStatisticsDocument(user.userId, userStatisticsDocument) && storageExtension.createUser(user);
        LOGGER.info("User statistics document and user document have been created: " + String.valueOf(success));
        return success;
    }

    @Override
    public Optional<UserStatistics> getUserOverview(String userId) {
        return storageExtension.getUserOverview(userId);
    }

    @Override
    public List<String> getUserNews(String userId) {
        return storageExtension.getUserNews(userId);
    }

    @Override
    public boolean storeNews(String userId, String news) {
        return storageExtension.storeNews(userId, news);
    }

    @Override
    public boolean storeUserStatisticsDocument(String userId, UserStatistics userStatistics) {
        return storageExtension.storeUserStatisticsDocument(userId, userStatistics);
    }

    @Override
    public boolean notifyLiveTrip(LiveTrip liveTrip) {
        List<PassengerRideReference> passengers = routeService.getPassengers(liveTrip.userId, liveTrip.routeId, liveTrip.day);
        for (PassengerRideReference passenger : passengers) {
            Optional<User> user = getUserById(passenger.passengerUserId);
            // send email to user.mail;
            // set that the driver/passengers can receive reviews.
        }

        return true;
    }

    @Override
    public boolean processScore(EnrichedTripSummary enrichedTripSummary) {
        LOGGER.info("processing new score for user: " + enrichedTripSummary.userId);
        Optional<UserStatistics> userStatisticsOpt = storageExtension.getUserOverview(enrichedTripSummary.userId);
        if (!userStatisticsOpt.isPresent()){
            LOGGER.warn("Failed to retrieve user Statistics.");
            return false;
        }
        UserStatistics userStatistics = userStatisticsOpt.get();
        userStatistics.updateStatistics(enrichedTripSummary);
        LOGGER.info(String.format("User statistic document for user %s has been fetched and locally updated.", enrichedTripSummary.userId));
        boolean success = storageExtension.storeUserStatisticsDocument(enrichedTripSummary.userId, userStatistics);
        LOGGER.info(String.format("Score processed with success: %s, for user %s", String.valueOf(success), enrichedTripSummary.userId));
        return success;
    }
}
