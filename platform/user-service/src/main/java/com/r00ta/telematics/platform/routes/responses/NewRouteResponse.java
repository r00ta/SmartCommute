package com.r00ta.telematics.platform.routes.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewRouteResponse {

    @JsonProperty("routeId")
    public String routeId;

    public NewRouteResponse() {
    }

    public NewRouteResponse(String routeId) {
        this.routeId = routeId;
    }
}
