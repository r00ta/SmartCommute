package com.r00ta.telematics.platform.users.api;

import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.r00ta.telematics.platform.routes.responses.DayRidePassengersResponse;
import com.r00ta.telematics.platform.users.IUserService;
import com.r00ta.telematics.platform.users.models.User;
import com.r00ta.telematics.platform.users.models.UserStatistics;
import com.r00ta.telematics.platform.users.requests.NewUserRequest;
import com.r00ta.telematics.platform.users.responses.NewUserResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/users")
public class UserApi {

    @Inject
    IUserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Creates a new user.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = NewUserResponse.class))),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @Operation(summary = "Creates a new user.", description = "Creates a new user.")
    public Response createNewUser(NewUserRequest userRequest) {
        String userId = UUID.randomUUID().toString();
        User user = new User(userRequest, userId);
        boolean success = userService.createUser(user);
        if (success) {
            return Response.ok(new NewUserResponse(userId)).build();
        }
        return Response.status(400, "Can't create the user").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}")
    @APIResponses(value = {
            @APIResponse(description = "Gets user statistics", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = UserStatistics.class))),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @Operation(summary = "Gets user statistics", description = "Gets user statistics.")
    // TODO REPLACE WITH RESPONSE
    public Response getUserOverview(@PathParam("userId") String userId) {
        return Response.ok(userService.getUserOverview(userId)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/news")
    // todo docs.
    // TODO REPLACE WITH RESPONSE
    public Response getUserNews(@PathParam("userId") String userId) {
        return Response.ok(userService.getUserNews(userId)).build();
    }
}
