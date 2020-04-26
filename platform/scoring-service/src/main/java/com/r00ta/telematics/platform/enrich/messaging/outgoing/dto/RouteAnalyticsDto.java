package com.r00ta.telematics.platform.enrich.messaging.outgoing.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.enrich.models.EnrichedGpsLocation;
import com.r00ta.telematics.platform.enrich.models.EnrichedTrip;
import com.r00ta.telematics.platform.messaging.model.CloudEventDto;

public class RouteAnalyticsDto implements CloudEventDto {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("routeId")
    public String routeId;

    @JsonProperty("positions")
    public List<EnrichedGpsLocation> positions;

    @JsonProperty("startTimestamp")
    public Long startTimestamp;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";

    public RouteAnalyticsDto(EnrichedTrip enrichedTrip) {
        this.userId = enrichedTrip.userId;
        this.routeId = enrichedTrip.tripId;
        this.positions = enrichedTrip.positions;
        this.startTimestamp = enrichedTrip.startTimestamp;
    }

    @Override
    public String getEventId() {
        return null;
    }

    @Override
    public String getEventProducer() {
        return "Enrichment-scoring";
    }
}
