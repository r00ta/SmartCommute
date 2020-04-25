package com.r00ta.telematics.platform.users.api;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.platform.routes.responses.DayRidePassengersResponse;
import com.r00ta.telematics.platform.users.IUserService;
import com.r00ta.telematics.platform.users.UserService;
import com.r00ta.telematics.platform.users.models.User;
import com.r00ta.telematics.platform.users.models.UserStatistics;
import com.r00ta.telematics.platform.users.requests.NewUserRequest;
import com.r00ta.telematics.platform.users.responses.NewUserResponse;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirements;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/users")
@SecurityScheme(securitySchemeName = "jwt", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "jwt")
public class UserApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserApi.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Inject
    IUserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Creates a new user.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = NewUserResponse.class))),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @PermitAll
    @Operation(summary = "Creates a new user.", description = "Creates a new user.")
    public Response createNewUser(NewUserRequest userRequest) throws JsonProcessingException {
        String userId = UUID.randomUUID().toString();
        User user = new User(userRequest, userId);
        boolean success = userService.createUser(user);
        if (success) {
            return Response.ok(new NewUserResponse(userId)).build();
        }
        LOGGER.warn("Can't create user. Request: " + mapper.writeValueAsString(userRequest));
        return Response.status(400, "Can't create the user").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}")
    @APIResponses(value = {
            @APIResponse(description = "Gets user statistics", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = UserStatistics.class))),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @RolesAllowed({"Admin", "Tester", "User"})
    @SecurityRequirement(name = "jwt", scopes = {})
    @Operation(summary = "Gets user statistics", description = "Gets user statistics.")
    // TODO REPLACE WITH RESPONSE
    public Response getUserOverview(@PathParam("userId") String userId, @Context SecurityContext ctx) {
        if (!isUserRequestingHisData(userId, ctx)){
            return Response.ok().status(401, "User is requesting data of another user.").build();
        }

        Optional<UserStatistics> userOverview = userService.getUserOverview(userId);
        if (!userOverview.isPresent()){
            return Response.status(400, "User not found.").build();
        }

        return Response.ok(userOverview.get()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/news")
    @RolesAllowed({"Admin", "Tester", "User"})
    @SecurityRequirement(name = "jwt", scopes = {})
    // todo docs.
    // TODO REPLACE WITH RESPONSE
    public Response getUserNews(@PathParam("userId") String userId,@Context SecurityContext ctx) {
        if (!isUserRequestingHisData(userId, ctx)){
            return Response.ok().status(401, "User is requesting data of another user.").build();
        }

        return Response.ok(userService.getUserNews(userId)).build();
    }

    private boolean isUserRequestingHisData(String userId, SecurityContext ctx){
        return userId.equals(ctx.getUserPrincipal().getName());
    }
}
