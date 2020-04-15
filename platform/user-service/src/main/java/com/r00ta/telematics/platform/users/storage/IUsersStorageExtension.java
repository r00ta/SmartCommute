package com.r00ta.telematics.platform.users.storage;

import java.util.List;

import com.r00ta.telematics.platform.users.models.User;
import com.r00ta.telematics.platform.users.models.UserStatistics;

public interface IUsersStorageExtension {

    boolean createUser(User user);

    User getUserById(String userId);

    boolean storeUserStatisticsDocument(String userId, UserStatistics userStatistics);

    UserStatistics getUserOverview(String userId);

    List<String> getUserNews(String userId);

    boolean storeNews(String userId, String news);
}
