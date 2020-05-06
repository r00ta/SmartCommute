package com.r00ta.telematics.platform.enrich.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrichedTripHeader {
    @JsonProperty("tripId")
    public String tripId;

    @JsonProperty("startTimestamp")
    public Long startTimestamp;

    @JsonProperty("durationInMilliseconds")
    public Long durationInMilliseconds;

    @JsonProperty("distanceInM")
    public Float distanceInM;

    @JsonProperty("score")
    public Float score;

    @JsonProperty("startLocation")
    public String startLocation;

    @JsonProperty("endLocation")
    public String endLocation;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";

    public static String[] getDocumentProperties(){
        return new String[]{"tripId", "startTimestamp", "durationInMilliseconds", "distanceInM", "score", "startLocation", "endLocation"};
    }
}
