package com.r00ta.telematics.platform.routes.storage.codecs;

import com.r00ta.telematics.platform.routes.models.PassengerRideReference;
import com.r00ta.telematics.platform.routes.models.Route;
import com.r00ta.telematics.platform.users.models.User;
import com.r00ta.telematics.platform.users.models.UserStatistics;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class RouteCodecsProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == Route.class) {
            return (Codec<T>) new RouteCodecs(Route.class);
        }

        return null;
    }
}
