package com.r00ta.telematics.platform.live.messaging.dto;

import java.time.DayOfWeek;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.live.models.LiveSessionSummary;
import com.r00ta.telematics.platform.messaging.model.CloudEventDto;

public class LiveModelDto implements CloudEventDto {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("routeId")
    public String routeId;

    @JsonProperty("transientTripId")
    public String transientTripId;

    @JsonProperty("day")
    public DayOfWeek day;

    @JsonProperty("urlLiveTrip")
    public String urlLiveTrip;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";

    public LiveModelDto() {
    }

    public LiveModelDto(LiveSessionSummary sessionSummary) {
        this.userId = sessionSummary.userId;
        this.transientTripId = sessionSummary.sessionId;
        this.routeId = sessionSummary.routeId;
        this.day = sessionSummary.day;
        this.urlLiveTrip = sessionSummary.urlLiveTrip;
    }

    @Override
    public String getEventId() {
        return transientTripId;
    }

    @Override
    public String getEventProducer() {
        return "LiveTripService";
    }
}
