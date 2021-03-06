package com.r00ta.telematics.platform.live.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiveChunksResponse {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("sessionId")
    public String sessionId;

    @JsonProperty("chunks")
    public List<LiveChunkResponse> chunks;

    @JsonProperty("isLive")
    public boolean isLive;
}
