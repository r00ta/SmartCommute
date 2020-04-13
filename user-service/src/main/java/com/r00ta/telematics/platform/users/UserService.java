package com.r00ta.telematics.platform.users;

import java.util.List;

import javax.inject.Inject;

import com.r00ta.telematics.platform.users.models.User;
import com.r00ta.telematics.platform.users.models.UserStatistics;
import com.r00ta.telematics.platform.users.storage.IUsersStorageExtension;

public class UserService implements IUserService {

    @Inject
    IUsersStorageExtension storageExtension;

    @Override
    public User getUserById(String user) {
        return storageExtension.getUserById(user);
    }

    @Override
    public boolean createUser(User user) {
        boolean successOverview = storageExtension.createUserStatisticsDocument(user.userId);
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
}
