package com.r00ta.telematics.platform.live.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LiveSessionSummary {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("sessionId")
    public String sessionId;

    @JsonProperty("isLive")
    public boolean isLive;

    @JsonProperty("lastUpdate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    public Date lastUpdate;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";

    public LiveSessionSummary(String userId, String sessionId, boolean isLive, Date lastUpdate) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.isLive = isLive;
        this.lastUpdate = lastUpdate;
    }

    public LiveSessionSummary() {
    }
}
