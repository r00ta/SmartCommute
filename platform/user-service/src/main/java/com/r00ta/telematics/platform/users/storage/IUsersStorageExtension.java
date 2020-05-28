package com.r00ta.telematics.platform.users.storage;

import java.util.List;
import java.util.Optional;

import com.r00ta.telematics.platform.users.models.News;
import com.r00ta.telematics.platform.users.models.User;
import com.r00ta.telematics.platform.users.models.UserStatistics;

public interface IUsersStorageExtension {

    boolean createUser(User user);

    Optional<User> getUserById(String userId);

    Optional<User> getUserByEmail(String email);

    boolean storeUserStatisticsDocument(String userId, UserStatistics userStatistics);

    boolean updateUserStatisticsDocument(String userId, UserStatistics userStatistics);

    Optional<UserStatistics> getUserOverview(String userId);

    List<News> getUserNews(String userId);

    boolean storeNews(String userId, News news);
}
