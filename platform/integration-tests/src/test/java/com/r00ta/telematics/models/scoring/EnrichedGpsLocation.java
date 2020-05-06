package com.r00ta.telematics.models.scoring;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
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
}