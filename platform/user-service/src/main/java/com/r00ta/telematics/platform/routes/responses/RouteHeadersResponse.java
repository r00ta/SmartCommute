package com.r00ta.telematics.platform.routes.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.routes.models.RouteHeader;

public class RouteHeadersResponse {

    @JsonProperty("routeHeaders")
    public List<RouteHeader> routeHeaders;

    public RouteHeadersResponse() {
    }

    public RouteHeadersResponse(List<RouteHeader> headers) {
        this.routeHeaders = headers;
    }
}
