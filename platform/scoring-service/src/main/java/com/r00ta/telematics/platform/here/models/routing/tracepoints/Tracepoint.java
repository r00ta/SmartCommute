package com.r00ta.telematics.platform.here.models.routing.tracepoints;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tracepoint {

    /**
     * Confidence value for this point match (probability of match correctness). Value between 0.0 (high risk of matching to a wrong link) and 1.0 (very likely the matched link is the correct one). Currently the confidence value is simply derived from the distance between the original and the matched coordinate, but this will be refined in future releases.
     */
    @JsonProperty("confidenceValue")
    public Float confidenceValue;

    /**
     * Original GPS trace point elevation in meter height above the WGS84 reference ellipsoid. This value is taken from the request, and provided in the response for convenience.
     */
    @JsonProperty("elevation")
    public Float elevation;

    /**
     * Original GPS trace point heading (aka bearing or direction), in degree against North, clockwise. This value is taken from the request, and provided in the response for convenience. 10000 if not provided in the trace.
     */
    @JsonProperty("headingDegreeNorthClockwise")
    public Float headingDegreeNorthClockwise;

    /**
     * Heading of the road section at the matched position. Degree against North, clockwise.
     */
    @JsonProperty("headingMatched")
    public Float headingMatched;

    /**
     * Original GPS trace point latitude in WGS84 degree. This value is taken from the request, and provided in the response for convenience.
     */
    @JsonProperty("lat")
    public Float lat;

    /**
     * Matched GPS trace point latitude in WGS84 degree. The coordinate is guaranteed to fit (with a 0.00001 degree tolerance) onto the map road network, where the map shape points are connected by straight lines.
     */
    @JsonProperty("latMatched")
    public Float latMatched;

    /**
     * Matched Permanent Link ID for this trace point. Negative, if the link was driven in negative direction (from non-reference node to reference node).
     */
    @JsonProperty("linkIdMatched")
    public Long linkIdMatched;

    /**
     * Original GPS trace point longitude in WGS84 degree. This value is taken from the request, and provided in the response for convenience.
     */
    @JsonProperty("lon")
    public Float lon;

    /**
     * Matched GPS trace point longitude in WGS84 degree. The coordinate is guaranteed to fit (with a 0.00001 degree tolerance) onto the map road network, where the map shape points are connected by straight lines.
     */
    @JsonProperty("lonMatched")
    public Float lonMatched;

    /**
     * Missing doc.
     */
    @JsonProperty("matchDistance")
    public Float matchDistance;

    /**
     * Relative offset along the link to the matched coordinate, when driving from reference node towards non-reference node.
     */
    @JsonProperty("matchOffsetOnLink")
    public Float matchOffsetOnLink;

    /**
     * Meter distance to the nearest road. This is a lower bound for the combined inaccuracy of GPS signal and map. 1000000.0 if it was not computed for this point.
     */
    @JsonProperty("minError")
    public Float minError;

    /**
     * Missing doc.
     */
    @JsonProperty("routeLinkSeqNrMatched")
    public Integer routeLinkSeqNrMatched;

    /**
     * Original GPS trace point speed value, in meter per second (independent of the originally provided unit). This value is taken from the request, and provided in the response for convenience.
     */
    @JsonProperty("speedMps")
    public Float speedMps;

    /**
     * Milli seconds since 1/1/1970 UTC, unmodified from original trace, zero if trace contained no timestamps.
     */
    @JsonProperty("timestamp")
    public Long timestamp;
}