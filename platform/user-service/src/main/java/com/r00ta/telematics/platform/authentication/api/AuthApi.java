package com.r00ta.telematics.platform.authentication.api;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.r00ta.telematics.platform.authentication.IAuthService;
import com.r00ta.telematics.platform.authentication.requests.AuthenticationRequest;
import com.r00ta.telematics.platform.authentication.responses.AuthenticationResponse;
import com.r00ta.telematics.platform.users.models.User;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/users")
public class AuthApi {

    @Inject
    IAuthService authService;

    @POST
    @Path("/auth")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(description = "Authenticate a user, returning a jwt to access the resources of the platform.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = AuthenticationResponse.class))),
            @APIResponse(description = "Unouthorized", responseCode = "401", content = @Content(mediaType = MediaType.TEXT_PLAIN)),
            @APIResponse(description = "Bad request.", responseCode = "500", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    @Operation(summary = "Authenticate a user, returning a jwt to access the resources of the platform.", description = "Authenticate a user, returning a jwt to access the resources of the platform.")
    public Response authUser(AuthenticationRequest userRequest) throws Exception {
        Optional<User> user = authService.getUserByEmail(userRequest.email);
        if (!user.isPresent()) {
            return Response.status(400, "User not found.").build();
        }

        if (user.get().passwordHash.equals(userRequest.password)) {
            String token = authService.generateToken(user.get().userId);
            return Response.ok(new AuthenticationResponse(user.get().userId, token)).build();
        } else {
            return Response.status(401, "Wrong password.").build();
        }
    }
}
