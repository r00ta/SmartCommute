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

import com.r00ta.telematics.platform.enrich.IEnrichService;
import com.r00ta.telematics.platform.enrich.models.EnrichedTrip;
import com.r00ta.telematics.platform.enrich.models.TripModel;
import com.r00ta.telematics.platform.enrich.responses.EnrichedTripsByTimeRangeResponse;

@Path("/users/{userId}")
public class EnrichApi {

    @Inject
    IEnrichService enrichService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/enrichedTrips")
    public Response getTripsByTimeRange(@PathParam("userId") String userId, @QueryParam("from") @NotNull Long from, @QueryParam("to") @NotNull Long to) {
        return Response.ok(new EnrichedTripsByTimeRangeResponse(enrichService.getTripsByTimeRange(userId, from, to))).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/enrichedTrips/{tripId}")
    public Response getEnrichedTripById(@PathParam("userId") String userId, @QueryParam("tripId") @NotNull String tripId) {
        return Response.ok(enrichService.getTrip(userId, tripId)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/enrichTrip")
    public Response storeNewTrip(@PathParam("userId") String userId, TripModel trip) {
        EnrichedTrip enrichedTrip = enrichService.storeTrip(userId, trip);
        return Response.ok(enrichedTrip).build();
    }
}
