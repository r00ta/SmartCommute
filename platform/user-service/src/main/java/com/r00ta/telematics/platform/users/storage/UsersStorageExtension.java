package com.r00ta.telematics.platform.users.storage;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.platform.IStorageManager;
import com.r00ta.telematics.platform.users.models.User;
import com.r00ta.telematics.platform.users.models.UserStatistics;

// TODO: check application scoped? Request scoped?
@ApplicationScoped
public class UsersStorageExtension implements IUsersStorageExtension {

    private static final String USER_INDEX = "userindex";
    private static final String USER_STATISTICS_INDEX = "userstatisticsindex";
    private static final String USER_NEWS_INDEX = "usersnewsindex";

    private static ObjectMapper objectMapper;

    @Inject
    IStorageManager storageManager;

    @PostConstruct
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public boolean createUser(User user) {
        try {
            storageManager.create(user.userId, objectMapper.writeValueAsString(user), USER_INDEX);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Optional<User> getUserById(String userId) {
        String request = "{ \n" +
                "    \"query\": {\n" +
                "        \"match\": { \"userId\" : \"" + userId + "\"}\n" +
                "    }\n" +
                "}\n";
        List<User> users = storageManager.search(request, USER_INDEX, User.class);
        return users.isEmpty() ? null : Optional.of(users.get(0));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        String request = "{ \n" +
                "    \"query\": {\n" +
                "        \"match\": { \"email\" : \"" + email + "\"}\n" +
                "    }\n" +
                "}\n";
        List<User> users = storageManager.search(request, USER_INDEX, User.class);
        return users.isEmpty() ? null : Optional.of(users.get(0));
    }

    @Override
    public boolean storeUserStatisticsDocument(String userId, UserStatistics userStatistics) {
        try {
            storageManager.create(userId, objectMapper.writeValueAsString(userStatistics), USER_STATISTICS_INDEX);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Optional<UserStatistics> getUserOverview(String userId) {
        String request = "{ \n" +
                "    \"query\": {\n" +
                "        \"match\": { \"userId\" : \"" + userId + "\"}\n" +
                "    }\n" +
                "}\n";
        List<UserStatistics> users = storageManager.search(request, USER_STATISTICS_INDEX, UserStatistics.class);
        return users.isEmpty() ? null : Optional.of(users.get(0));
    }

    @Override
    public List<String> getUserNews(String userId) {
        String request = "{ \n" +
                "    \"query\": {\n" +
                "        \"match\": { \"userId\" : \"" + userId + "\"}\n" +
                "    }\n" +
                "}\n";
        return storageManager.search(request, USER_NEWS_INDEX, String.class);
    }

    @Override
    public boolean storeNews(String userId, String news) {
        try {
            storageManager.create(userId, objectMapper.writeValueAsString(news), USER_NEWS_INDEX);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}