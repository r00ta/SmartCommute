package com.r00ta.telematics.platform.live.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiveSessionModel {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("sessionId")
    public String sessionId;

    @JsonProperty("routeId")
    public String routeId;

    @JsonProperty("chunks")
    public List<LiveChunkModel> chunks;

    @JsonProperty("isLive")
    public boolean isLive;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";

    public LiveSessionModel(String userId, String sessionId, String routeId, boolean isLive, List<LiveChunkModel> chunks) {
        this.userId = userId;
        this.routeId = routeId;
        this.sessionId = sessionId;
        this.isLive = isLive;
        this.chunks = chunks;
    }

    public LiveSessionModel(String userId, String sessionId, String routeId, boolean isLive) {
        this(userId, sessionId, routeId, isLive, new ArrayList<>());
    }

    public LiveSessionModel() {
        // Intentionally left blank
    }

    public String getUserId() {
        return userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public boolean getIsLive() {
        return isLive;
    }

    public List<LiveChunkModel> getChunks() {
        return chunks;
    }
}
