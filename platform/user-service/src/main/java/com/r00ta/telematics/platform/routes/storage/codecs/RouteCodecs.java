package com.r00ta.telematics.platform.routes.storage.codecs;

import com.r00ta.telematics.platform.mongo.DocumentCodec;
import com.r00ta.telematics.platform.routes.models.Route;
import com.r00ta.telematics.platform.users.models.User;

public class RouteCodecs extends DocumentCodec<Route> {

    public RouteCodecs(Class<Route> clazz) {
        super(clazz);
    }
}
