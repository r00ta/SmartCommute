package com.r00ta.telematics.platform.enrich.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.here.models.attributes.Attributes;
import com.r00ta.telematics.platform.here.models.attributes.SpeedLimitAttribute;
import com.r00ta.telematics.platform.here.models.tracepoints.Tracepoint;

public class EnrichedGpsLocation {

    @JsonProperty("longitude")
    public Float longitude;

    @JsonProperty("latitude")
    public Float latitude;

    @JsonProperty("elevation")
    public Float elevation;

    @JsonProperty("speedMps")
    public Float speedMps;

    @JsonProperty("confidenceValue")
    public Float confidenceValue;

    @JsonProperty("timestamp")
    public Long timestamp;

    @JsonProperty("metadata")
    public EnrichedMetadata metadata;

    @JsonProperty("linkId")
    public Long linkId;

    @JsonProperty("matchDistance")
    public Float matchDistance;

    @JsonProperty("pointScore")
    public Float pointScore;

    public EnrichedGpsLocation(Tracepoint tracepoint, Attributes attributes) {
        this.longitude = tracepoint.lonMatched;
        this.latitude = tracepoint.latMatched;
        this.confidenceValue = tracepoint.confidenceValue;
        this.elevation = tracepoint.elevation;
        this.speedMps = tracepoint.speedMps;
        this.timestamp = tracepoint.timestamp;
        this.linkId = tracepoint.linkIdMatched;
        this.matchDistance = tracepoint.matchDistance;
        if (attributes.speedLimitAttribute1s != null && attributes.speedLimitAttribute1s.size() != 0) {
            SpeedLimitAttribute speedLimitAttribute = attributes.speedLimitAttribute1s.get(0);
            this.metadata = new EnrichedMetadata(speedLimitAttribute.fromSpeedLimit, speedLimitAttribute.toSpeedLimit);
        }
    }

    public EnrichedGpsLocation(){}

}
