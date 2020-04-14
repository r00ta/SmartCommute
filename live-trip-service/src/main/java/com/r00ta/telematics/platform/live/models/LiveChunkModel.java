package com.r00ta.telematics.platform.live.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiveChunkModel {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("sessionId")
    public String sessionId;

    @JsonProperty("chunkSeqNumber")
    public Long chunkSeqNumber;

    @JsonProperty("positions")
    public List<GpsLocation> positions;

    @JsonProperty("isLastChunk")
    public boolean isLastChunk;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";

    public LiveChunkModel() {
    }

    public LiveChunkModel(String userId, String sessionId, Long chunkSeqNumber, List<GpsLocation> positions, boolean isLastChunk) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.chunkSeqNumber = chunkSeqNumber;
        this.positions = positions;
        this.isLastChunk = isLastChunk;
    }
}
