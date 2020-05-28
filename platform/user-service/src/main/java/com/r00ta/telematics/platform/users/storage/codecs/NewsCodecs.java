package com.r00ta.telematics.platform.users.storage.codecs;

import com.r00ta.telematics.platform.mongo.DocumentCodec;
import com.r00ta.telematics.platform.users.models.News;
import com.r00ta.telematics.platform.users.models.User;

public class NewsCodecs extends DocumentCodec<News> {
    public NewsCodecs(Class<News> clazz) {
        super(clazz);
    }

}
