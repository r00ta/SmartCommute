package com.r00ta.telematics.platform.users.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.users.messaging.enriched.dto.EnrichedTripSummaryDto;

public class EnrichedTripSummary {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("tripId")
    public String tripId;

    @JsonProperty("score")
    public Float score;

    @JsonProperty("distanceInM")
    public Double distanceInM;

    @JsonProperty("duration")
    public Long duration;

    @JsonProperty("co2Saved")
    public Double co2Saved;

    public EnrichedTripSummary(EnrichedTripSummaryDto scoreModel) {
        this.userId = scoreModel.userId;
        this.tripId = scoreModel.tripId;
        this.score = scoreModel.score;
        this.distanceInM = scoreModel.distanceInM;
        this.duration = scoreModel.duration;
    }

    public EnrichedTripSummary() {
    }
}
