package com.r00ta.telematics.platform.live.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.live.models.GpsLocation;

public class LiveChunkResponse {

    @JsonProperty("chunkSeqNumber")
    public Long chunkSeqNumber;

    @JsonProperty("positions")
    public List<GpsLocation> positions;

    public LiveChunkResponse() {
    }

    public LiveChunkResponse(Long chunkSeqNumber, List<GpsLocation> positions) {
        this.chunkSeqNumber = chunkSeqNumber;
        this.positions = positions;
    }
}
