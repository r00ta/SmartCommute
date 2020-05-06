package com.r00ta.telematics.platform.users.storage.codecs;

import com.r00ta.telematics.platform.mongo.DocumentCodec;
import com.r00ta.telematics.platform.users.models.UserStatistics;

public class UserStatisticsCodecs extends DocumentCodec<UserStatistics> {

    public UserStatisticsCodecs(Class<UserStatistics> clazz) {
        super(clazz);
    }
}