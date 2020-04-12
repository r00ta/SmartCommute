package com.r00ta.telematics.platform.here.models.routelinks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.here.models.attributes.Attributes;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteLink {

    /**
     * Permanent Link ID. Negative, if the link was driven in negative direction (from non-reference node to reference node).
     */
    @JsonProperty("linkId")
    public Long linkId;

    /**
     * Road functional class. Required to efficiently lookup further link attributes from the PDE Web service.
     */
    @JsonProperty("functionalClass")
    public Integer functionalClass;

    /**
     * String that contains a blank separated list of latitude/longitude coordinates in WGS84 degree. The order of the coordinates is in route driving direction. One of the coordinates is required to efficiently lookup further link attributes from the PDE Web service.
     */
    @JsonProperty("shape")
    public String shape;

    /**
     * Length of the link in meters. This is provided for convenience to compute statistics. Link length is computed upon linear straight connections between the shape points (not spherical).
     */
    @JsonProperty("linkLength")
    public Float linkLength;

    /**
     * Estimated relative drive time in milli seconds needed from the route start point to the start of this link. Note that this time is currently not aligned with time stamp values provided in the trace.
     */
    @JsonProperty("mSecToReachLinkFromStart")
    public Integer mSecToReachLinkFromStart;

    /**
     * Confidence value for this link (probability that this link was actually driven). Value between 0.0 (high risk that link was not driven but is an artefact from big GPS deviations or missing road geometry) and 1.0 (very likely the link was driven). Currently the confidence value is derived from the confidence of the matched way points on the link or the confidence of the neighbour links, but this might be refined in future releases.
     */
    @JsonProperty("confidence")
    public Float confidence;

    /**
     * Only present for the first and last link on the route as well as links involved in u-turns. Specifies which part of the link was actually driven. The offset is seen from reference node. Example1: If the start link is driven to reference node with offset=0.4 then this link got driven from 0.4 to 0.0 (from reference node). Example2: If the first link of a u-turn is driven from reference node with offset=0.3 then this link got driven from 0.0 to 0.3 (from reference node). Example3: If the first link of a u-turn is driven to reference node with offset=0.3 then this link got driven from 1.0 to 0.3 (from reference node). Example4: If the second link of a u-turn is driven from reference node with offset=0.3 then this link got driven from 0.3 to 1.0 (from reference node).
     */
    @JsonProperty("offset")
    public Integer offset;

    /**
     * Attributes
     */
    @JsonProperty
    public Attributes attributes;
}
