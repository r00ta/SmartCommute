package com.r00ta.telematics.platform.live.storage;

import java.util.List;

import com.r00ta.telematics.platform.live.model.LiveChunkModel;
import com.r00ta.telematics.platform.live.model.LiveSessionSummary;

public interface ILiveStorageExtension {
    boolean createNewLiveSession(LiveSessionSummary sessionSummary);

    LiveSessionSummary getLiveSessionSummary(String sessionId);

    List<LiveSessionSummary> getAvailableLiveSessionSummaries(String userId);

    List<LiveChunkModel> getLiveSessionChunks(String userId, String sessionId, Long lastChunk);

    boolean updateLiveSession(String sessionId, LiveChunkModel chunk);
}
