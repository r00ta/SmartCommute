package com.r00ta.telematics.platform.mocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.r00ta.telematics.platform.users.IUserService;
import com.r00ta.telematics.platform.users.models.EnrichedTripSummary;
import com.r00ta.telematics.platform.users.models.LiveTrip;
import com.r00ta.telematics.platform.users.models.News;
import com.r00ta.telematics.platform.users.models.User;
import com.r00ta.telematics.platform.users.models.UserStatistics;

public class UserServiceMock implements IUserService {

    private Map<String, User> storage = new HashMap<>();

    private Map<String, List<News>> newsStorage = new HashMap<>();

    @Override
    public Optional<User> getUserById(String user) {
        return Optional.of(storage.get(user));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return null;
    }

    @Override
    public boolean createUser(User user) {
        storage.put(user.userId, user);
        return true;
    }

    @Override
    public Optional<UserStatistics> getUserOverview(String userId) {
        return null;
    }

    @Override
    public List<News> getUserNews(String userId) {
        if (newsStorage.containsKey(userId)) {
            return newsStorage.get(userId);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean storeNews(String userId, News news) {
        if (newsStorage.containsKey(userId)) {
            return newsStorage.get(userId).add(news);
        }
        newsStorage.put(userId, new ArrayList<>());
        newsStorage.get(userId).add(news);
        return true;
    }

    @Override
    public boolean processScore(EnrichedTripSummary enrichedTripSummary) {
        return false;
    }

    @Override
    public boolean storeUserStatisticsDocument(String userId, UserStatistics userStatistics) {
        return false;
    }

    @Override
    public boolean notifyLiveTrip(LiveTrip liveTrip) {
        return false;
    }
}
