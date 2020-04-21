package com.r00ta.telematics.platform.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.messaging.incoming.dto.RouteModelDto;

public class AnalyticsRoute {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("tripId")
    public String tripId;

    @JsonProperty("positions")
    public List<EnrichedGpsLocation> positions;

    @JsonProperty("startTimestamp")
    public Long startTimestamp;


    public static AnalyticsRoute from(RouteModelDto route){
        AnalyticsRoute a = new AnalyticsRoute();
        a.userId = route.userId;
        a.tripId = route.tripId;
        a.positions = route.positions;
        a.startTimestamp = route.startTimestamp;
        return a;
    }

}
