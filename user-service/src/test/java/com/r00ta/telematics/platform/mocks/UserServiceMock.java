package com.r00ta.telematics.platform.mocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.r00ta.telematics.platform.routes.models.Route;
import com.r00ta.telematics.platform.users.IUserService;
import com.r00ta.telematics.platform.users.models.User;
import com.r00ta.telematics.platform.users.models.UserStatistics;

public class UserServiceMock implements IUserService {

    private Map<String, User> storage = new HashMap<>();

    private Map<String, List<String>> newsStorage = new HashMap<>();

    @Override
    public User getUserById(String user) {
        return storage.get(user);
    }

    @Override
    public boolean createUser(User user) {
        storage.put(user.userId, user);
        return true;
    }

    @Override
    public UserStatistics getUserOverview(String userId) {
        return null;
    }

    @Override
    public List<String> getUserNews(String userId) {
        if (newsStorage.containsKey(userId)){
            return newsStorage.get(userId);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean storeNews(String userId, String news) {
        if (newsStorage.containsKey(userId)){
            return newsStorage.get(userId).add(news);
        }
        newsStorage.put(userId, new ArrayList<>());
        newsStorage.get(userId).add(news);
        return true;
    }
}
