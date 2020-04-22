package com.r00ta.telematics.platform.trips.api;

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

import com.r00ta.telematics.platform.trips.ITripService;
import com.r00ta.telematics.platform.trips.models.TripModel;
import com.r00ta.telematics.platform.trips.requests.NewTripRequest;
import com.r00ta.telematics.platform.trips.responses.TripsByTimeRangeResponse;
import com.r00ta.telematics.platform.trips.responses.TripsHeadersByTimeRangeResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/users/{userId}")
public class TripApi {

    @Inject
    ITripService tripService;

    @GET
    @Path("/tripsHeaders")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Gets trip headers of a user.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = TripsHeadersByTimeRangeResponse.class))),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @Operation(summary = "Gets trip headers of a user.", description = "Gets trip headers of a user.")
    public Response getTripsHeaders(@PathParam("userId") String userId, @QueryParam("from") @NotNull Long from, @QueryParam("to") Long to) {
        return Response.ok(new TripsHeadersByTimeRangeResponse(tripService.getTripsHeadersByTimeRange(userId, from, to))).build();
    }

    @GET
    @Path("/trips")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Gets trips by time range.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = TripsByTimeRangeResponse.class))),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @Operation(summary = "Gets trips by time range.", description = "Gets trips by time range.")
    public Response getTripsByTimeRange(@PathParam("userId") String userId, @QueryParam("from") @NotNull Long from, @QueryParam("to") @NotNull Long to) {
        return Response.ok(new TripsByTimeRangeResponse(tripService.getTripsByTimeRange(userId, from, to))).build();
    }

    @POST
    @Path("/trips/{tripId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Stores a new trip.", responseCode = "200"),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @Operation(summary = "Stores a new trip", description = "Stores a new trip")
    public Response storeNewTrip(@PathParam("userId") String userId, @PathParam("tripId") String tripId, NewTripRequest trip) {
        TripModel model = new TripModel(userId, tripId, trip.routeId, trip.startTimestamp, trip.positions, trip.engineRpmSamples);
        tripService.storeAndSendTripAsync(userId, model);
        return Response.ok().build();
    }

    @GET
    @Path("/trips/{tripId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Gets a trip by id.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = TripModel.class))),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @Operation(summary = "Gets a trip by id.", description = "Gets a trip by id.")
    public Response getTripById(@PathParam("userId") String userId, @PathParam("tripId") String tripId) {
        return Response.ok(tripService.getTrip(userId, tripId)).build();
    }
}
