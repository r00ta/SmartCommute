package com.r00ta.telematics.platform.users;

import java.util.List;

import com.r00ta.telematics.platform.users.models.EnrichedTripSummary;
import com.r00ta.telematics.platform.users.models.LiveTrip;
import com.r00ta.telematics.platform.users.models.User;
import com.r00ta.telematics.platform.users.models.UserStatistics;

public interface IUserService {

    User getUserById(String user);

    boolean createUser(User user);

    UserStatistics getUserOverview(String userId);

    List<String> getUserNews(String userId);

    boolean storeNews(String userId, String news);

    boolean processScore(EnrichedTripSummary enrichedTripSummary);

    boolean storeUserStatisticsDocument(String userId, UserStatistics userStatistics);

    boolean notifyLiveTrip(LiveTrip liveTrip);
}
