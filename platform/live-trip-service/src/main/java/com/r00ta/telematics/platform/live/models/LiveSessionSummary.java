package com.r00ta.telematics.platform.live.models;

import java.time.DayOfWeek;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.SmartDocument;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveSessionSummary extends SmartDocument {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("sessionId")
    public String sessionId;

    @JsonProperty("routeId")
    public String routeId;

    @JsonProperty("day")
    public DayOfWeek day;

    @JsonProperty("urlLiveTrip")
    public String urlLiveTrip;

    @JsonProperty("isLive")
    public boolean isLive;

    @JsonProperty("lastUpdate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    public Date lastUpdate;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";

    public LiveSessionSummary(String userId, String sessionId, String routeId, DayOfWeek day, boolean isLive, Date lastUpdate) {
        this.userId = userId;
        this.day = day;
        this.sessionId = sessionId;
        this.routeId = routeId;
        this.isLive = isLive;
        this.lastUpdate = lastUpdate;
    }

    public LiveSessionSummary() {
    }
}
