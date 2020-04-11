package com.r00ta.telematics.platform.live.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.r00ta.telematics.platform.live.ILiveService;
import com.r00ta.telematics.platform.live.model.LiveChunkModel;
import com.r00ta.telematics.platform.live.model.LiveSessionSummary;
import com.r00ta.telematics.platform.live.requests.LiveChunkUpdateRequest;
import com.r00ta.telematics.platform.live.responses.AvailableLiveSessionsResponse;
import com.r00ta.telematics.platform.live.responses.LiveChunkResponse;
import com.r00ta.telematics.platform.live.responses.LiveChunksResponse;

@Path("/users/{userId}/liveSessions")
@RequestScoped
public class LiveApi {

    @Inject
    ILiveService liveService;

    // TODO: SET RETENTION POLICY
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewSession(@PathParam("userId") String userId) {
        LiveSessionSummary summary = liveService.createNewLiveSession(userId);
        return Response.ok(summary).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableSessions(@PathParam("userId") String userId) {
        return Response.ok(new AvailableLiveSessionsResponse(liveService.getAvailableLiveSessions(userId))).build();
    }

    @PUT
    @Path("/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateLiveSession(@PathParam("userId") String userId, @PathParam("sessionId") String sessionId, LiveChunkUpdateRequest request) {
        LiveChunkModel model = new LiveChunkModel(userId, sessionId, request.chunkSeqNumber, request.positions, request.isLastChunk);
        boolean success = liveService.updateLiveSession(userId, sessionId, model);
        return Response.ok().build();
    }

    @GET
    @Path("/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getLiveSession(@PathParam("userId") String userId, @PathParam("sessionId") String sessionId, @QueryParam("lastChunk") Long lastChunk) {
        List<LiveChunkModel> liveSessionChunks = liveService.getLiveSessionChunks(userId, sessionId, lastChunk);
        LiveChunksResponse response = new LiveChunksResponse();
        response.sessionId = sessionId;
        response.userId = userId;
        response.isLive = liveSessionChunks.size() != 0 ? !liveSessionChunks.stream().anyMatch(x -> x.isLastChunk) : liveService.getLiveSessionSummary(sessionId).isLive;
        response.chunks = liveSessionChunks.stream().map(x -> new LiveChunkResponse(x.chunkSeqNumber, x.positions)).collect(Collectors.toList());
        return Response.ok(response).build();
    }
}