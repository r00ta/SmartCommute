package com.r00ta.telematics.platform.live.requests;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.live.models.GpsLocation;

public class LiveChunkUpdateRequest {
    @JsonProperty("chunkSeqNumber")
    public Long chunkSeqNumber;

    @JsonProperty("isLastChunk")
    public boolean isLastChunk;

    @JsonProperty("positions")
    public List<GpsLocation> positions;

    public LiveChunkUpdateRequest(Long chunkSeqNumber, boolean isLastChunk, List<GpsLocation> positions) {
        this.chunkSeqNumber = chunkSeqNumber;
        this.isLastChunk = isLastChunk;
        this.positions = positions;
    }

    public LiveChunkUpdateRequest(String userId, String sessionId, boolean isLastChunk, Long chunkSeqNumber) {
        this(chunkSeqNumber, isLastChunk, new ArrayList<>());
    }

    public LiveChunkUpdateRequest() {
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