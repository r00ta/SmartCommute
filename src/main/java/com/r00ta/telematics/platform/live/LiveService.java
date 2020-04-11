package com.r00ta.telematics.platform.live;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.r00ta.telematics.platform.database.IStorageManager;
import com.r00ta.telematics.platform.live.model.LiveChunkModel;
import com.r00ta.telematics.platform.live.model.LiveSessionModel;
import com.r00ta.telematics.platform.live.model.LiveSessionSummary;
import com.r00ta.telematics.platform.live.storage.LiveStorageExtension;

@ApplicationScoped
public class LiveService implements ILiveService {

    @Inject
    LiveStorageExtension storageManager;

    @Override
    // TODO: sanity checks
    public LiveSessionSummary createNewLiveSession(String userId) {
        String sessionId = UUID.randomUUID().toString();
        LiveSessionSummary model = new LiveSessionSummary(userId, sessionId, true, new Date(OffsetDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli()));
        storageManager.createNewLiveSession(model);
        return model;
    }

    @Override
    // TODO: sanity checks
    public boolean updateLiveSession(String userId, String sessionId, LiveChunkModel chunk) {
        boolean success = storageManager.updateLiveSession(sessionId, chunk);
        return success;
    }


    @Override
    public LiveSessionSummary getLiveSessionSummary(String sessionId) {
        return storageManager.getLiveSessionSummary(sessionId);
    }

    @Override
    public List<LiveSessionSummary> getAvailableLiveSessions(String userId) {
        return storageManager.getAvailableLiveSessionSummaries(userId);
    }

    @Override
    public List<LiveChunkModel> getLiveSessionChunks(String userId, String sessionId, Long lastChunk) {
        return storageManager.getLiveSessionChunks(userId, sessionId, lastChunk);
    }
}