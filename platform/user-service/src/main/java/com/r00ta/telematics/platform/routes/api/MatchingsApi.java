package com.r00ta.telematics.platform.routes.api;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.r00ta.telematics.platform.routes.IRouteService;
import com.r00ta.telematics.platform.routes.models.PendingMatching;
import com.r00ta.telematics.platform.routes.models.RouteMatching;
import com.r00ta.telematics.platform.routes.requests.UpdatePendingMatchingRequest;
import com.r00ta.telematics.platform.routes.responses.AvailableMatchingsResponse;
import com.r00ta.telematics.platform.routes.responses.NewRouteResponse;
import com.r00ta.telematics.platform.users.messaging.enriched.EnrichedTripKafkaConsumer;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/users/{userId}/matchings")
@SecurityScheme(securitySchemeName = "jwt", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "jwt")
public class MatchingsApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchingsApi.class);

    @Inject
    IRouteService routeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Gets the available matchings for a route", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = AvailableMatchingsResponse.class))),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @RolesAllowed({"Admin", "Tester", "User"})
    @SecurityRequirement(name = "jwt", scopes = {})
    @Operation(summary = "Gets the available matchings", description = "Gets the available matchings for a route")
    // TODO REPLACE WITH RESPONSE
    public Response getMatchings(@PathParam("userId") String userId, @PathParam("routeId") String routeId, @Context SecurityContext ctx) {
        if (!isUserRequestingHisData(userId, ctx)) {
            return Response.ok().status(401, "User is requesting data of another user.").build();
        }

        List<PendingMatching> matchings = routeService.getPendingMatchings(userId);

        return Response.ok(new AvailableMatchingsResponse(matchings)).build();
    }

    @POST
    @APIResponses(value = {
            @APIResponse(description = "Process a new Matching", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT))),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @RolesAllowed({"Admin", "Tester", "User"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @SecurityRequirement(name = "jwt", scopes = {})
    @Operation(summary = "Process a new Matching", description = "Process a new Matching")
    public Response processMatching(@PathParam("userId") String userId, RouteMatching routeMatching, @Context SecurityContext ctx) {
        routeService.processMatching(routeMatching);
        return Response.ok().build();
    }

    @PUT
    @Path("/{matchingId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Accepts or reject a matching", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @RolesAllowed({"Admin", "Tester", "User"})
    @SecurityRequirement(name = "jwt", scopes = {})
    @Operation(summary = "Accepts or reject a matching", description = "Accepts or reject a matching")
    // TODO REPLACE WITH RESPONSE
    public Response updateMatching(@PathParam("userId") String userId, @PathParam("matchingId") String matchingId, UpdatePendingMatchingRequest request, @Context SecurityContext ctx) {
        if (!isUserRequestingHisData(userId, ctx)) {
            return Response.ok().status(401, "User is requesting data of another user.").build();
        }

        boolean response = routeService.updatePendingMatching(userId, matchingId, request.status);

        if (!response) {
            return Response.status(400, "Something went wrong.").build();
        }

        return Response.ok().build();
    }



    private boolean isUserRequestingHisData(String userId, SecurityContext ctx) {
        return userId.equals(ctx.getUserPrincipal().getName());
    }
}
