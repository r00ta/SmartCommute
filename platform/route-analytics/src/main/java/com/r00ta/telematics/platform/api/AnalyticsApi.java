package com.r00ta.telematics.platform.api;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.r00ta.telematics.platform.IAnalyticsService;
import com.r00ta.telematics.platform.requests.LoadAnalysisRequest;

@Path("/analytics")
public class AnalyticsApi {

    @Inject
    IAnalyticsService analyticsService;

    @POST
    @Path("loadResults")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loadResultsWebhook(LoadAnalysisRequest request) {
        analyticsService.processAnalysisResults(request.itemName);
        return Response.ok().build();
    }
}
