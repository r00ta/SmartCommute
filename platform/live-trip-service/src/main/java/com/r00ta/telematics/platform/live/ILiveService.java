package com.r00ta.telematics.platform.live;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import com.r00ta.telematics.platform.live.models.LiveChunkModel;
import com.r00ta.telematics.platform.live.models.LiveSessionSummary;

public interface ILiveService {

    LiveSessionSummary createNewLiveSession(String userId, String routeId, DayOfWeek day);

    boolean updateLiveSession(String userId, String sessionId, LiveChunkModel chunk);

    Optional<LiveSessionSummary> getLiveSessionSummary(String sessionId);

    List<LiveSessionSummary> getAvailableLiveSessions(String userId);

    List<LiveChunkModel> getLiveSessionChunks(String userId, String sessionId, Long lastChunk);
}
