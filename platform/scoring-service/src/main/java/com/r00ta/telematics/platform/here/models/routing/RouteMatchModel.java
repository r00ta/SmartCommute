package com.r00ta.telematics.platform.here.models.routing;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.here.models.routing.routelinks.RouteLink;
import com.r00ta.telematics.platform.here.models.routing.tracepoints.Tracepoint;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteMatchModel {

    @JsonProperty("RouteLinks")
    public List<RouteLink> routeLinks;

    @JsonProperty("TracePoints")
    public List<Tracepoint> tracePoints;

    @JsonProperty("Warnings")
    public List<Warning> warnings;

    @JsonProperty("MapVersion")
    public String mapVersion;
}