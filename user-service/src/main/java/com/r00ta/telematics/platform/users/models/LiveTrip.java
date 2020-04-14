package com.r00ta.telematics.platform.users.models;

import java.time.DayOfWeek;

import com.r00ta.telematics.platform.users.messaging.live.dto.LiveTripSummaryDto;

public class LiveTrip {

    public String userId;

    public String routeId;

    public DayOfWeek day;

    public String urlLiveTrip;

    public LiveTrip(LiveTripSummaryDto liveTrip) {
        this.userId = liveTrip.userId;
        this.routeId = liveTrip.routeId;
        this.day = liveTrip.day;
        this.urlLiveTrip = liveTrip.urlLiveTrip;
    }

    public LiveTrip() {
    }
}
