package com.r00ta.telematics.platform.enrich.messaging.outgoing.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.enrich.models.EnrichedTrip;
import com.r00ta.telematics.platform.messaging.model.CloudEventDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrichedTripSummaryDto implements CloudEventDto {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("tripId")
    public String tripId;

    @JsonProperty("score")
    public Float score;

    @JsonProperty("distanceInM")
    public Float distanceInM;

    @JsonProperty("duration")
    public Long duration;

    @JsonProperty("co2Saved")
    public Double co2Saved;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";

    public EnrichedTripSummaryDto() {
    }

    public EnrichedTripSummaryDto(EnrichedTrip enrichedTrip) {
        this.userId = enrichedTrip.userId;
        this.tripId = enrichedTrip.tripId;
        this.score = enrichedTrip.score;
        this.distanceInM = enrichedTrip.distanceInM;
        this.duration = enrichedTrip.durationInMilliseconds;
        this.co2Saved = 0d;
    }

    @Override
    public String getEventId() {
        return tripId;
    }

    @Override
    public String getEventProducer() {
        return "Enrichment-scoring";
    }
}
