package com.r00ta.telematics.platform.live.api;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import com.r00ta.telematics.platform.live.models.LiveChunkModel;
import com.r00ta.telematics.platform.live.models.LiveSessionSummary;
import com.r00ta.telematics.platform.live.requests.LiveChunkUpdateRequest;
import com.r00ta.telematics.platform.live.responses.AvailableLiveSessionsResponse;
import com.r00ta.telematics.platform.live.responses.LiveChunkResponse;
import com.r00ta.telematics.platform.live.responses.LiveChunksResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/users/{userId}/liveSessions")
@RequestScoped
public class LiveApi {

    @Inject
    ILiveService liveService;

    // TODO: SET RETENTION POLICY
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Create a new live session", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = LiveSessionSummary.class))),
            @APIResponse(description = "Internal server error", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @Operation(summary = "Creates a new live session and gets the created object.", description = "Creates a new live session and gets the created object.")
    public Response createNewSession(@PathParam("userId") String userId, @QueryParam("routeId") String routeId, @QueryParam("day") DayOfWeek day) {
        LiveSessionSummary summary = liveService.createNewLiveSession(userId, routeId, day);
        return Response.ok(summary).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Gets available sessions for a user.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = AvailableLiveSessionsResponse.class))),
            @APIResponse(description = "Internal server error", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @Operation(summary = "Gets available sessions for a user.", description = "Gets available sessions for a user.")
    public Response getAvailableSessions(@PathParam("userId") String userId) {
        return Response.ok(new AvailableLiveSessionsResponse(liveService.getAvailableLiveSessions(userId))).build();
    }

    @PUT
    @Path("/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Updates a live session.", responseCode = "200"),
            @APIResponse(description = "Bad request.", responseCode = "400", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @Operation(summary = "Updates a live session.", description = "Updates a live session.")
    public Response updateLiveSession(@PathParam("userId") String userId, @PathParam("sessionId") String sessionId, LiveChunkUpdateRequest request) {
        LiveChunkModel model = new LiveChunkModel(userId, sessionId, request.chunkSeqNumber, request.positions, request.isLastChunk);
        boolean success = liveService.updateLiveSession(userId, sessionId, model);
        if (success) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), "Bad Request.").build();
    }

    @GET
    @Path("/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Gets chunks of a live session.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = LiveChunksResponse.class))),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @Operation(summary = "Gets chunks of a live session.", description = "Gets chunks of a live session.")
    public Response getLiveSession(@PathParam("userId") String userId, @PathParam("sessionId") String sessionId, @QueryParam("lastChunk") Long lastChunk) {
        try {
            List<LiveChunkModel> liveSessionChunks = liveService.getLiveSessionChunks(userId, sessionId, lastChunk);
            LiveChunksResponse response = new LiveChunksResponse();
            response.sessionId = sessionId;
            response.userId = userId;
            if (liveSessionChunks != null && liveSessionChunks.size() != 0) {
                response.isLive = !liveSessionChunks.stream().anyMatch(x -> x.isLastChunk);
                response.chunks = liveSessionChunks.stream().map(x -> new LiveChunkResponse(x.chunkSeqNumber, x.positions)).collect(Collectors.toList());
            } else {
                Optional<LiveSessionSummary> summary = liveService.getLiveSessionSummary(sessionId);
                if (!summary.isPresent()) {
                    return Response.status(400, "Live session summary not found.").build();
                }
                response.isLive = summary.get().isLive;
                response.chunks = new ArrayList<>();
            }
            return Response.ok(response).build();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return Response.status(500, "something bad").build();
    }
}