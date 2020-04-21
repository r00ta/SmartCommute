package com.r00ta.telematics.platform.models;

import com.r00ta.telematics.platform.messaging.incoming.dto.RouteModelDto;

public class AnalyticsRoute {

    public static AnalyticsRoute from(RouteModelDto route){
        return new AnalyticsRoute();
    }

}
