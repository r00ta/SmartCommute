package com.r00ta.telematics.platform.live.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiveSessionSummary {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("sessionId")
    public String sessionId;

    @JsonProperty("isLive")
    public boolean isLive;

    @JsonProperty("lastUpdate")
    public Date lastUpdate;

    public LiveSessionSummary(String userId, String sessionId, boolean isLive, Date lastUpdate){
        this.userId = userId;
        this.sessionId = sessionId;
        this.isLive = isLive;
        this.lastUpdate = lastUpdate;
    }

    public LiveSessionSummary(){}
}
