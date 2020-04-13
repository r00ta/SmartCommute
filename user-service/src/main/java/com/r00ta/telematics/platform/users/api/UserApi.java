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

import com.r00ta.telematics.platform.users.IUserService;
import com.r00ta.telematics.platform.users.models.User;
import com.r00ta.telematics.platform.users.requests.NewUserRequest;
import com.r00ta.telematics.platform.users.responses.NewUserResponse;

@Path("/users")
public class UserApi {

    @Inject
    IUserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
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
    // TODO REPLACE WITH RESPONSE
    public Response getUserOverview(@PathParam("userId") String userId) {
        return Response.ok(userService.getUserOverview(userId)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userId}/news")
    // TODO REPLACE WITH RESPONSE
    public Response getUserNews(@PathParam("userId") String userId) {
        return Response.ok(userService.getUserNews(userId)).build();
    }
}
