package com.r00ta.telematics.platform.routes.api;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.r00ta.telematics.platform.routes.IRouteService;
import com.r00ta.telematics.platform.routes.models.Route;
import com.r00ta.telematics.platform.routes.models.RouteHeader;
import com.r00ta.telematics.platform.routes.requests.NewRouteRequest;
import com.r00ta.telematics.platform.routes.responses.DayRidePassengersResponse;
import com.r00ta.telematics.platform.routes.responses.NewRouteResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@Path("/users/{userId}/routes")
@SecurityScheme(securitySchemeName = "jwt", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "jwt")
public class RouteApi {

    @Inject
    IRouteService routeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // TODO REPLACE WITH PROPER RESPONSE
    @APIResponses(value = {
            @APIResponse(description = "Gets user's routes.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = RouteHeader.class))),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @RolesAllowed({"Admin", "Tester", "User"})
    @SecurityRequirement(name = "jwt", scopes = {})
    @Operation(summary = "Gets user's routes.", description = "Gets user's routes.")
    public Response getUserRoutes(@PathParam("userId") String userId,  @Context SecurityContext ctx) {
        if (!isUserRequestingHisData(userId, ctx)){
            return Response.ok().status(401, "User is requesting data of another user.").build();
        }

        List<Route> userRoutes = routeService.getUserRoutes(userId);
        List<RouteHeader> userRoutesHeaders = userRoutes.stream().map(x -> new RouteHeader(x)).collect(Collectors.toList());
        return Response.ok(userRoutesHeaders).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Create a new route for a user.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = NewRouteResponse.class))),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @RolesAllowed({"Admin", "Tester", "User"})
    @SecurityRequirement(name = "jwt", scopes = {})
    @Operation(summary = "Create a new route for a user.", description = "Create a new route for a user.")
    public Response createNewRoute(@PathParam("userId") String userId, NewRouteRequest routeRequest,  @Context SecurityContext ctx) {
        if (!isUserRequestingHisData(userId, ctx)){
            return Response.ok().status(401, "User is requesting data of another user.").build();
        }

        String routeId = UUID.randomUUID().toString();
        Route route = new Route(userId, routeRequest, routeId);
        boolean success = routeService.storeRoute(route);
        if (success) {
            return Response.ok(new NewRouteResponse(routeId)).build();
        }
        return Response.status(400, "Can't create the route.").build();
    }

    @GET
    @Path("/{routeId}")
    @Produces(MediaType.APPLICATION_JSON)
    // TODO REPLACE WITH RESPONSE
    @APIResponses(value = {
            @APIResponse(description = "Gets route by id.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Route.class))),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @RolesAllowed({"Admin", "Tester", "User"})
    @SecurityRequirement(name = "jwt", scopes = {})
    @Operation(summary = "Gets route by id.", description = "Gets route by id.")
    public Response getRouteById(@PathParam("userId") String userId, @PathParam("routeId") String routeId,  @Context SecurityContext ctx) {
        if (!isUserRequestingHisData(userId, ctx)){
            return Response.ok().status(401, "User is requesting data of another user.").build();
        }

        return Response.ok(routeService.getRouteById(routeId)).build();
    }

    @GET
    @Path("/{routeId}/days/{dayOfTheWeek}/passengers")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Gets passenger's names for a driver's route", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = DayRidePassengersResponse.class))),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @RolesAllowed({"Admin", "Tester", "User"})
    @SecurityRequirement(name = "jwt", scopes = {})
    @Operation(summary = "Gets passenger's names for a driver's route", description = "Gets passenger's names for a driver's route")
    // TODO REPLACE WITH RESPONSE
    public Response getPassengers(@PathParam("userId") String userId, @PathParam("routeId") String routeId, @PathParam("dayOfTheWeek") String dayOfTheWeek,  @Context SecurityContext ctx) {
        if (!isUserRequestingHisData(userId, ctx)){
            return Response.ok().status(401, "User is requesting data of another user.").build();
        }

        List<String> passengers = routeService.getPassengers(userId, routeId, DayOfWeek.FRIDAY).stream().map(x -> x.passengerName).collect(Collectors.toList());

        return Response.ok(new DayRidePassengersResponse(passengers)).build();
    }

    @DELETE
    @Path("/{routeId}/days/{dayOfTheWeek}/passengers/{passengerId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Deletes a passenger from a driver's route.", responseCode = "200"),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @RolesAllowed({"Admin", "Tester", "User"})
    @SecurityRequirement(name = "jwt", scopes = {})
    @Operation(summary = "Deletes a passenger from a driver's route.", description = "Deletes a passenger from a driver's route.")
    // TODO REPLACE WITH RESPONSE
    public Response deletePassenger(@PathParam("userId") String userId, @PathParam("routeId") String routeId, @PathParam("dayOfTheWeek") String dayOfTheWeek, @PathParam("passengerId") String passengerId,  @Context SecurityContext ctx) {
        if (!isUserRequestingHisData(userId, ctx)){
            return Response.ok().status(401, "User is requesting data of another user.").build();
        }

        boolean success = routeService.deletePassenger(userId, routeId, passengerId, new DayOfWeek[]{DayOfWeek.FRIDAY});
        if (!success) {
            return Response.status(400, "Something went wrong.").build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/{routeId}/days/{dayOfTheWeek}/driver/{driverId}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Deletes a driver from a passenger's route.", responseCode = "200"),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @RolesAllowed({"Admin", "Tester", "User"})
    @SecurityRequirement(name = "jwt", scopes = {})
    @Operation(summary = "Deletes a driver from a passenger's route.", description = "Deletes a driver from a passenger's route.")
    // TODO REPLACE WITH RESPONSE
    public Response deleteDriver(@PathParam("userId") String userId, @PathParam("routeId") String routeId, @PathParam("dayOfTheWeek") String dayOfTheWeek, @PathParam("driverId") String driverId,  @Context SecurityContext ctx) {
        if (!isUserRequestingHisData(userId, ctx)){
            return Response.ok().status(401, "User is requesting data of another user.").build();
        }

        boolean success = routeService.deletePassenger(userId, routeId, driverId, new DayOfWeek[]{DayOfWeek.FRIDAY});
        if (!success) {
            return Response.status(400, "Something went wrong.").build();
        }
        return Response.ok().build();
    }

    private boolean isUserRequestingHisData(String userId, SecurityContext ctx){
        return userId.equals(ctx.getUserPrincipal().getName());
    }
}
