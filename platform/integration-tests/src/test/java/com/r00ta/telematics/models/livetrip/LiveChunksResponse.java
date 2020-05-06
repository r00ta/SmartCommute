package com.r00ta.telematics.models.livetrip;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
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
