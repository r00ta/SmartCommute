package com.r00ta.telematics.platform.trips.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.SmartDocument;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripSummaryModel extends SmartDocument {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("tripId")
    public String tripId;

    @JsonProperty("startLocation")
    public GpsLocation startLocation;

    @JsonProperty("endLocation")
    public GpsLocation endLocation;

    @JsonProperty("startTimestamp")
    public Long startTimestamp;

    @JsonProperty("endDatetime")
    public Long endTimestamp;

    @JsonProperty("millisecondsDuration")
    public Long millisecondsDuration;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";

    public TripSummaryModel() {
    }

    public TripSummaryModel(String userId, String tripId, GpsLocation startLocation, GpsLocation endLocation, Long startTimestamp, Long endTimestamp, Long millisecondsDuration) {
        this.userId = userId;
        this.tripId = tripId;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.millisecondsDuration = millisecondsDuration;
    }
}
