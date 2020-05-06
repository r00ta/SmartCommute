package com.r00ta.telematics.platform.users.storage;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.platform.IStorageManager;
import com.r00ta.telematics.platform.SmartQuery;
import com.r00ta.telematics.platform.operators.StringOperator;
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
        storageManager.create(user.userId, user, USER_INDEX);
        return true;
    }

    @Override
    public Optional<User> getUserById(String userId) {
        SmartQuery query = new SmartQuery().where("userId", StringOperator.EQUALS, userId);
        List<User> users = storageManager.search(query, USER_INDEX, User.class);
        return users.isEmpty() ? null : Optional.of(users.get(0));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        SmartQuery query = new SmartQuery().where("email", StringOperator.EQUALS, email);
        List<User> users = storageManager.search(query, USER_INDEX, User.class);
        return users.isEmpty() ? null : Optional.of(users.get(0));
    }

    @Override
    public boolean storeUserStatisticsDocument(String userId, UserStatistics userStatistics) {
        storageManager.create(userId, userStatistics, USER_STATISTICS_INDEX);
        return true;
    }

    @Override
    public Optional<UserStatistics> getUserOverview(String userId) {
        SmartQuery query = new SmartQuery().where("userId", StringOperator.EQUALS, userId);
        List<UserStatistics> users = storageManager.search(query, USER_STATISTICS_INDEX, UserStatistics.class);
        return users.isEmpty() ? null : Optional.of(users.get(0));
    }

    @Override
    public List<String> getUserNews(String userId) {
        SmartQuery query = new SmartQuery().where("userId", StringOperator.EQUALS, userId);
        return storageManager.search(query, USER_NEWS_INDEX, String.class);
    }

    @Override
    public boolean storeNews(String userId, String news) {
        return storageManager.create(userId, news, USER_NEWS_INDEX);
    }
}