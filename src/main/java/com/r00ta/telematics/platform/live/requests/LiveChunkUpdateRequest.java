package com.r00ta.telematics.platform.live.requests;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.live.model.GpsLocation;

public class LiveChunkUpdateRequest {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("sessionId")
    public String sessionId;

    @JsonProperty("chunkSeqNumber")
    public Long chunkSeqNumber;

    @JsonProperty("isLastChunk")
    public boolean isLastChunk;

    @JsonProperty("positions")
    public List<GpsLocation> positions;

    public LiveChunkUpdateRequest(String userId, String sessionId, Long chunkSeqNumber, boolean isLastChunk, List<GpsLocation> positions) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.chunkSeqNumber = chunkSeqNumber;
        this.isLastChunk = isLastChunk;
        this.positions = positions;
    }

    public LiveChunkUpdateRequest(String userId, String sessionId, boolean isLastChunk, Long chunkSeqNumber) {
        this(userId, sessionId, chunkSeqNumber, isLastChunk, new ArrayList<>());
    }

    public LiveChunkUpdateRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public boolean getIsLastChunk() {
        return isLastChunk;
    }

    public Long getChunkSeqNumber() {
        return chunkSeqNumber;
    }

    public List<GpsLocation> getPositions() {
        return positions;
    }
}