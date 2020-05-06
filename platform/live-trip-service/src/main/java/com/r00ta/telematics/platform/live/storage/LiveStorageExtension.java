package com.r00ta.telematics.platform.live.storage;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.platform.IStorageManager;
import com.r00ta.telematics.platform.SmartQuery;
import com.r00ta.telematics.platform.live.models.LiveChunkModel;
import com.r00ta.telematics.platform.live.models.LiveSessionSummary;
import com.r00ta.telematics.platform.operators.LongOperator;
import com.r00ta.telematics.platform.operators.StringOperator;

// TODO: check application scoped? Request scoped?
@ApplicationScoped
public class LiveStorageExtension implements ILiveStorageExtension {

    private static final String LIVESUMMARYINDEX = "livesummarysessions";
    private static final String LIVECHUNKSINDEX = "livechunks";
    private static ObjectMapper objectMapper;

    @Inject
    IStorageManager storageManager;

    @PostConstruct
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public boolean createNewLiveSession(LiveSessionSummary sessionSummary) {
        return storageManager.create(sessionSummary.sessionId, sessionSummary, LIVESUMMARYINDEX);
    }

    @Override
    public Optional<LiveSessionSummary> getLiveSessionSummary(String sessionId) {
        SmartQuery query = new SmartQuery().where("sessionId", StringOperator.EQUALS, sessionId);
        List<LiveSessionSummary> summary = storageManager.search(query, LIVESUMMARYINDEX, LiveSessionSummary.class);
        return summary.isEmpty() ? null : Optional.of(summary.get(0));
    }

    @Override
    public boolean updateLiveSessionSummary(String sessionId, boolean isLive) {
        Optional<LiveSessionSummary> summaryOpt = getLiveSessionSummary(sessionId);
        if (!summaryOpt.isPresent()) {
            return false;
        }

        LiveSessionSummary summary = summaryOpt.get();
        summary.isLive = isLive;

        return storageManager.create(sessionId, summary, LIVESUMMARYINDEX);
    }

    @Override
    public List<LiveSessionSummary> getAvailableLiveSessionSummaries(String userId) {
        SmartQuery query = new SmartQuery().where("userId", StringOperator.EQUALS, userId);
        return storageManager.search(query, LIVESUMMARYINDEX, LiveSessionSummary.class);
    }

    @Override
    public List<LiveChunkModel> getLiveSessionChunks(String userId, String sessionId, Long lastChunk) {
        SmartQuery query = new SmartQuery().where("sessionId", StringOperator.EQUALS, sessionId).where("chunkSeqNumber", LongOperator.GTE, lastChunk).limit(10000);
        return storageManager.search(query, LIVECHUNKSINDEX, LiveChunkModel.class);
    }

    @Override
    public boolean updateLiveSession(String sessionId, LiveChunkModel chunk) {
        storageManager.create(sessionId + "-" + chunk.chunkSeqNumber, chunk, LIVECHUNKSINDEX);
        return true;
    }
}
