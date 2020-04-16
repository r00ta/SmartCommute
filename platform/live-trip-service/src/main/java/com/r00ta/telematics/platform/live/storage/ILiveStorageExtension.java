package com.r00ta.telematics.platform.live.storage;

import java.util.List;

import com.r00ta.telematics.platform.live.models.LiveChunkModel;
import com.r00ta.telematics.platform.live.models.LiveSessionSummary;

public interface ILiveStorageExtension {

    boolean createNewLiveSession(LiveSessionSummary sessionSummary);

    LiveSessionSummary getLiveSessionSummary(String sessionId);

    boolean updateLiveSessionSummary(String sessionId, boolean isLive);

    List<LiveSessionSummary> getAvailableLiveSessionSummaries(String userId);

    List<LiveChunkModel> getLiveSessionChunks(String userId, String sessionId, Long lastChunk);

    boolean updateLiveSession(String sessionId, LiveChunkModel chunk);
}
