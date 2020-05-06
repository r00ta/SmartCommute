package com.r00ta.telematics.platform.users.storage.codecs;

import com.r00ta.telematics.platform.users.models.User;
import com.r00ta.telematics.platform.users.models.UserStatistics;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class UserCodecsProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == User.class) {
            return (Codec<T>) new UserCodecs(User.class);
        }
        if (clazz == UserStatistics.class){
            return (Codec<T>) new UserStatisticsCodecs(UserStatistics.class);
        }
        return null;
    }
}
