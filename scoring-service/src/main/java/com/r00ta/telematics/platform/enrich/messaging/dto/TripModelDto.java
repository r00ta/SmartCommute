package com.r00ta.telematics.platform.enrich.messaging.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.enrich.models.GpsLocation;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripModelDto {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("tripId")
    public String tripId;

    @JsonProperty("positions")
    public List<GpsLocation> positions;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";
}
