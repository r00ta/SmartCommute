package com.r00ta.telematics.platform.live;

import java.util.List;

import com.r00ta.telematics.platform.live.model.LiveChunkModel;
import com.r00ta.telematics.platform.live.model.LiveSessionSummary;

public interface ILiveService {

    LiveSessionSummary createNewLiveSession(String userId);

    boolean updateLiveSession(String userId, String sessionId, LiveChunkModel chunk);

    LiveSessionSummary getLiveSessionSummary(String sessionId);

    List<LiveSessionSummary> getAvailableLiveSessions(String userId);

    List<LiveChunkModel> getLiveSessionChunks(String userId, String sessionId, Long lastChunk);
}
