package com.r00ta.telematics.platform.users;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.r00ta.telematics.platform.routes.IRouteService;
import com.r00ta.telematics.platform.routes.models.PassengerRideReference;
import com.r00ta.telematics.platform.users.models.EnrichedTripSummary;
import com.r00ta.telematics.platform.users.models.LiveTrip;
import com.r00ta.telematics.platform.users.models.User;
import com.r00ta.telematics.platform.users.models.UserStatistics;
import com.r00ta.telematics.platform.users.storage.IUsersStorageExtension;

@ApplicationScoped
public class UserService implements IUserService {

    @Inject
    IUsersStorageExtension storageExtension;

    @Inject
    IRouteService routeService;

    @Override
    public User getUserById(String user) {
        return storageExtension.getUserById(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return storageExtension.getUserByEmail(email);
    }

    @Override
    public boolean createUser(User user) {
        UserStatistics userStatisticsDocument = new UserStatistics(user.userId);
        boolean successOverview = storeUserStatisticsDocument(user.userId, userStatisticsDocument);
        if (successOverview) {
            return storageExtension.createUser(user);
        }
        return false;
    }

    @Override
    public UserStatistics getUserOverview(String userId) {
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
            User user = getUserById(passenger.passengerUserId);
            // send email to user.mail;
            // set that the driver/passengers can receive reviews.
        }

        return true;
    }

    @Override
    public boolean processScore(EnrichedTripSummary enrichedTripSummary) {
        UserStatistics userStatistics = storageExtension.getUserOverview(enrichedTripSummary.userId);

        userStatistics.updateStatistics(enrichedTripSummary);

        boolean success = storageExtension.storeUserStatisticsDocument(enrichedTripSummary.userId, userStatistics);

        return success;
    }
}
