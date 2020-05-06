package com.r00ta.telematics.platform.users.storage.codecs;

import com.r00ta.telematics.platform.mongo.DocumentCodec;
import com.r00ta.telematics.platform.users.models.User;

public class UserCodecs extends DocumentCodec<User> {

    public UserCodecs(Class<User> clazz) {
        super(clazz);
    }
}
