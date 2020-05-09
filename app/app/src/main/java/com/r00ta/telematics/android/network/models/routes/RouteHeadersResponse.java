package com.r00ta.telematics.android.network.models.routes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteHeadersResponse {
    @JsonProperty("routeHeaders")
    public List<RouteHeaderResponse> routeHeaders;

    public RouteHeadersResponse(){}

    public RouteHeadersResponse(List<RouteHeaderResponse> headers){
        this.routeHeaders = headers;
    }
}
