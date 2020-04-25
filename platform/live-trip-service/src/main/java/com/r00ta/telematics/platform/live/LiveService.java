package com.r00ta.telematics.platform.live;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.r00ta.telematics.platform.live.messaging.LiveTripKafkaProducer;
import com.r00ta.telematics.platform.live.messaging.dto.LiveModelDto;
import com.r00ta.telematics.platform.live.models.LiveChunkModel;
import com.r00ta.telematics.platform.live.models.LiveSessionSummary;
import com.r00ta.telematics.platform.live.storage.LiveStorageExtension;

@ApplicationScoped
public class LiveService implements ILiveService {

    @Inject
    LiveStorageExtension storageManager;

    @Inject
    LiveTripKafkaProducer liveTripKafkaProducer;

    @Override
    // TODO: sanity checks
    public LiveSessionSummary createNewLiveSession(String userId, String routeId, DayOfWeek day) {
        String sessionId = UUID.randomUUID().toString();
        LiveSessionSummary model = new LiveSessionSummary(userId, sessionId, routeId, day, true, new Date(OffsetDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli()));
        model.urlLiveTrip = "https://localhost:1337/index.html?userId=" + model.userId + "&sessionId=" + model.sessionId;
        storageManager.createNewLiveSession(model);
        liveTripKafkaProducer.sendEventAsync(new LiveModelDto(model));
        return model;
    }

    @Override
    // TODO: sanity checks
    public boolean updateLiveSession(String userId, String sessionId, LiveChunkModel chunk) {
        boolean success = storageManager.updateLiveSession(sessionId, chunk);
        if (chunk.isLastChunk){
            storageManager.updateLiveSessionSummary(sessionId, false);
        }
        return success;
    }

    @Override
    public Optional<LiveSessionSummary> getLiveSessionSummary(String sessionId) {
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