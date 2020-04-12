package com.r00ta.telematics.platform.enrich.scoring;

import java.util.List;

import com.r00ta.telematics.platform.enrich.models.EnrichedGpsLocation;
import com.r00ta.telematics.platform.enrich.models.EnrichedTrip;

public class DriverScoring {

    public static void setPointScores(EnrichedTrip enrichedTrip) {
        List<EnrichedGpsLocation> positions = enrichedTrip.positions;

        Float score = 0.0f;

        for (EnrichedGpsLocation position : positions) {
            double difference = position.metadata.fromSpeedLimit - position.speedMps;
            if (difference >= 0) {
                score += 1;
                position.pointScore = 1f;
                continue;
            }
            if (difference >= -2.7) { // 0-10 km/h over the speed limit
                // score += 0
                position.pointScore = -1f;
                continue;
            }
            if (difference >= -8.33) { // 10-30 km/h over the speed limit
                score += -0.50f;
                position.pointScore = -5f;
                continue;
            }
            // over 30km/h
            score += -1;
            position.pointScore = -10f;
        }

        enrichedTrip.score = Math.max(0, score / positions.size() * 100);
    }
}
