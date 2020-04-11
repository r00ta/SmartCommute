package com.r00ta.telematics.platform.live.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiveSessionModel {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("sessionId")
    public String sessionId;

    @JsonProperty("chunks")
    public List<LiveChunkModel> chunks;

    @JsonProperty("isLive")
    public boolean isLive;

    public LiveSessionModel(String userId, String sessionId, boolean isLive, List<LiveChunkModel> chunks) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.isLive = isLive;
        this.chunks = chunks;
    }

    public LiveSessionModel(String userId, String sessionId, boolean isLive) {
        this(userId, sessionId, isLive, new ArrayList<>());
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
