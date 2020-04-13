package com.r00ta.telematics.platform.routes.api;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.r00ta.telematics.platform.routes.IRouteService;
import com.r00ta.telematics.platform.routes.models.Route;
import com.r00ta.telematics.platform.routes.models.RouteHeader;
import com.r00ta.telematics.platform.routes.requests.NewRouteRequest;
import com.r00ta.telematics.platform.routes.responses.DayRidePassengersResponse;
import com.r00ta.telematics.platform.routes.responses.NewRouteResponse;

@Path("/users/{userId}/routes")
public class RouteApi {

    @Inject
    IRouteService routeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // TODO REPLACE WITH PROPER RESPONSE
    public Response getUserRoutes(@PathParam("userId") String userId) {
        List<Route> userRoutes = routeService.getUserRoutes(userId);
        List<RouteHeader> userRoutesHeaders = userRoutes.stream().map(x -> new RouteHeader(x)).collect(Collectors.toList());
        return Response.ok(userRoutesHeaders).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewRoute(NewRouteRequest routeRequest) {
        String routeId = UUID.randomUUID().toString();
        Route route = new Route(routeRequest, routeId);
        boolean success = routeService.storeRoute(route);
        if (success) {
            return Response.ok(new NewRouteResponse(routeId)).build();
        }
        return Response.status(400, "Can't create the route.").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{routeId}")
    // TODO REPLACE WITH RESPONSE
    public Response getRouteById(@PathParam("userId") String userId, @PathParam("routeId") String routeId) {
        return Response.ok(routeService.getRouteById(routeId)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{routeId}/days/{dayOfTheWeek}/passengers")
    // TODO REPLACE WITH RESPONSE
    public Response getPassengers(@PathParam("userId") String userId, @PathParam("routeId") String routeId, @PathParam("dayOfTheWeek") String dayOfTheWeek) {
        List<String> passengers = routeService.getPassengers(userId, routeId, DayOfWeek.FRIDAY).stream().map(x -> x.passengerName).collect(Collectors.toList());

        return Response.ok(new DayRidePassengersResponse(passengers)).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{routeId}/days/{dayOfTheWeek}/passengers/{passengerId}")
    // TODO REPLACE WITH RESPONSE
    public Response deletePassenger(@PathParam("userId") String userId, @PathParam("routeId") String routeId, @PathParam("dayOfTheWeek") String dayOfTheWeek, @PathParam("passengerId") String passengerId) {
        boolean success = routeService.deletePassenger(userId, routeId, passengerId, new DayOfWeek[]{DayOfWeek.FRIDAY});
        if (!success) {
            return Response.status(400, "Something went wrong.").build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{routeId}/days/{dayOfTheWeek}/driver/{driverId}")
    // TODO REPLACE WITH RESPONSE
    public Response deleteDriver(@PathParam("userId") String userId, @PathParam("routeId") String routeId, @PathParam("dayOfTheWeek") String dayOfTheWeek, @PathParam("driverId") String driverId) {
        boolean success = routeService.deletePassenger(userId, routeId, driverId, new DayOfWeek[]{DayOfWeek.FRIDAY});
        if (!success) {
            return Response.status(400, "Something went wrong.").build();
        }
        return Response.ok().build();
    }
}
