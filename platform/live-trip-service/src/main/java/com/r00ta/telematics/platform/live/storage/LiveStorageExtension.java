package com.r00ta.telematics.platform.live.storage;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.platform.IStorageManager;
import com.r00ta.telematics.platform.live.models.LiveChunkModel;
import com.r00ta.telematics.platform.live.models.LiveSessionSummary;

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
        try {
            storageManager.create(sessionSummary.sessionId, objectMapper.writeValueAsString(sessionSummary), LIVESUMMARYINDEX);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Optional<LiveSessionSummary> getLiveSessionSummary(String sessionId) {
        String request = "{ \n" +
                "    \"query\": {\n" +
                "        \"match\": { \"sessionId\" : \"" + sessionId + "\"}\n" +
                "    }\n" +
                "}\n";

        List<LiveSessionSummary> summary = storageManager.search(request, LIVESUMMARYINDEX, LiveSessionSummary.class);
        return summary.isEmpty() ? null : Optional.of(summary.get(0));
    }

    @Override
    public boolean updateLiveSessionSummary(String sessionId, boolean isLive) {
        Optional<LiveSessionSummary> summaryOpt = getLiveSessionSummary(sessionId);
        if (!summaryOpt.isPresent()){
            return false;
        }

        LiveSessionSummary summary = summaryOpt.get();
        summary.isLive = isLive;

        try {
            storageManager.create(sessionId, objectMapper.writeValueAsString(summary), LIVESUMMARYINDEX);
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<LiveSessionSummary> getAvailableLiveSessionSummaries(String userId) {
        String request = "{ \n" +
                "    \"query\": {\n" +
                "        \"match\": { \"userId\" : \"" + userId + "\"}\n" +
                "    }\n" +
                "}\n";
        return storageManager.search(request, LIVESUMMARYINDEX, LiveSessionSummary.class);
    }

    @Override
    public List<LiveChunkModel> getLiveSessionChunks(String userId, String sessionId, Long lastChunk) {
        String request = String.format(
                "{\"size\": 10000, \"query\" : { \"bool\": {\n" +
                        "          \"must\": [{\"match\": { \"sessionId\" : \"" + sessionId + "\"} }," +
                        "{\"range\" : {\"chunkSeqNumber\" : {\"gte\" : %d}}}" +
                        " ] } } }", lastChunk);
        return storageManager.search(request, LIVECHUNKSINDEX, LiveChunkModel.class);
    }

    @Override
    public boolean updateLiveSession(String sessionId, LiveChunkModel chunk) {
        try {
            String request = objectMapper.writeValueAsString(chunk);
            storageManager.create(sessionId + "-" + chunk.chunkSeqNumber, request, LIVECHUNKSINDEX);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return true;
    }
}
