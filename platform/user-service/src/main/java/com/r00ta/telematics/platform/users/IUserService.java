package com.r00ta.telematics.platform.users;

import java.util.List;
import java.util.Optional;

import com.r00ta.telematics.platform.users.models.EnrichedTripSummary;
import com.r00ta.telematics.platform.users.models.LiveTrip;
import com.r00ta.telematics.platform.users.models.News;
import com.r00ta.telematics.platform.users.models.User;
import com.r00ta.telematics.platform.users.models.UserStatistics;

public interface IUserService {

    Optional<User> getUserById(String user);

    Optional<User> getUserByEmail(String email);

    boolean createUser(User user);

    Optional<UserStatistics> getUserOverview(String userId);

    List<News> getUserNews(String userId);

    boolean storeNews(String userId, News news);

    boolean processScore(EnrichedTripSummary enrichedTripSummary);

    boolean storeUserStatisticsDocument(String userId, UserStatistics userStatistics);

    boolean notifyLiveTrip(LiveTrip liveTrip);
}
