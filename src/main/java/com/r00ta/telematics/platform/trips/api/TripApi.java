package com.r00ta.telematics.platform.trips.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.r00ta.telematics.platform.live.model.LiveChunkModel;
import com.r00ta.telematics.platform.live.requests.LiveChunkUpdateRequest;
import com.r00ta.telematics.platform.live.responses.AvailableLiveSessionsResponse;
import com.r00ta.telematics.platform.live.responses.LiveChunkResponse;
import com.r00ta.telematics.platform.live.responses.LiveChunksResponse;
import com.r00ta.telematics.platform.trips.ITripService;
import com.r00ta.telematics.platform.trips.models.TripModel;
import com.r00ta.telematics.platform.trips.requests.NewTripRequest;
import com.r00ta.telematics.platform.trips.responses.TripsByTimeRangeResponse;
import com.r00ta.telematics.platform.trips.responses.TripsHeadersByTimeRangeResponse;
import org.jboss.resteasy.annotations.Query;

@Path("/users/{userId}")
public class TripApi {

    @Inject
    ITripService tripService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/tripsHeaders")
    public Response getTripsHeaders(@PathParam("userId") String userId, @QueryParam("from") @NotNull Long from, @QueryParam("to") Long to) {
        return Response.ok(new TripsHeadersByTimeRangeResponse(tripService.getTripsHeadersByTimeRange(userId, from, to))).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/trips")
    public Response getTripsByTimeRange(@PathParam("userId") String userId, @QueryParam("from") @NotNull Long from, @QueryParam("to") @NotNull Long to) {
        return Response.ok(new TripsByTimeRangeResponse(tripService.getTripsByTimeRange(userId, from, to))).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/trips/{tripId}")
    public Response storeNewTrip(@PathParam("userId") String userId, @PathParam("tripId") String tripId, NewTripRequest trip) {
        TripModel model = new TripModel(userId, tripId, trip.startTimestamp, trip.positions);
        tripService.storeTrip(userId, model);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/trips/{tripId}")
    public Response getAvailableSessions(@PathParam("userId") String userId, @PathParam("tripId") String tripId) {
        return Response.ok(tripService.getTrip(userId, tripId)).build();
    }
}
