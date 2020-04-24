package com.r00ta.telematics.platform.enrich.api;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.r00ta.telematics.platform.ElasticSearchStorageManager;
import com.r00ta.telematics.platform.enrich.IEnrichService;
import com.r00ta.telematics.platform.enrich.models.EnrichedTrip;
import com.r00ta.telematics.platform.enrich.models.TripModel;
import com.r00ta.telematics.platform.enrich.responses.EnrichedTripsByTimeRangeResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/users/{userId}")
public class EnrichApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnrichApi.class);

    @Inject
    IEnrichService enrichService;

    @GET
    @Path("/enrichedTrips")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Gets enriched trips of a user by time range.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = EnrichedTripsByTimeRangeResponse.class))),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @Operation(summary = "Gets enriched trips of a user by time range.", description = "Gets enriched trips of a user by time range.")
    public Response getTripsByTimeRange(@PathParam("userId") String userId, @QueryParam("from") @NotNull Long from, @QueryParam("to") @NotNull Long to) {
        return Response.ok(new EnrichedTripsByTimeRangeResponse(enrichService.getTripsByTimeRange(userId, from, to))).build();
    }

    @GET
    @Path("/enrichedTrips/{tripId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Gets an enriched trip by id.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = EnrichedTrip.class))),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @Operation(summary = "Gets an enriched trip by id.", description = "Gets an enriched trip by id.")
    public Response getEnrichedTripById(@PathParam("userId") String userId, @PathParam("tripId") String tripId) {
        return Response.ok(enrichService.getTrip(userId, tripId)).build();
    }

    @POST
    @Path("/enrichTrip")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Stores a new enriched trip (testing).", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = EnrichedTrip.class))),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @Operation(summary = "Stores a new enriched trip (testing).", description = "Stores a new enriched trip (testing).")
    public Response storeNewTrip(@PathParam("userId") String userId, TripModel trip) {
        EnrichedTrip enrichedTrip = enrichService.storeTrip(userId, trip);
        return Response.ok(enrichedTrip).build();
    }
}
