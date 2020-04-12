package com.r00ta.telematics.platform.enrich.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.here.models.RouteMatchModel;
import com.r00ta.telematics.platform.here.models.attributes.Attributes;
import com.r00ta.telematics.platform.here.models.routelinks.RouteLink;
import io.jenetics.jpx.Point;
import io.jenetics.jpx.WayPoint;
import io.jenetics.jpx.geom.Geoid;

public class EnrichedTrip {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("tripId")
    public String tripId;

    @JsonProperty("positions")
    public List<EnrichedGpsLocation> positions;

    @JsonProperty("startTimestamp")
    public Long startTimestamp;

    @JsonProperty("durationInMilliseconds")
    public Long durationInMilliseconds;

    @JsonProperty("distanceInM")
    public Float distanceInM;

    @JsonProperty("score")
    public Float score;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";

    public static EnrichedTrip fromRouteMatch(String userId, String tripId, RouteMatchModel routeMatch) {
        Map<Long, Attributes> attributesMap = buildAttributesMap(routeMatch.routeLinks);
        EnrichedTrip matchedTrip = new EnrichedTrip();
        matchedTrip.tripId = tripId;
        matchedTrip.userId = userId;
        matchedTrip.startTimestamp = routeMatch.tracePoints.get(0).timestamp;
        matchedTrip.positions = routeMatch.tracePoints.stream().map(x -> new EnrichedGpsLocation(x, attributesMap.get(x.linkIdMatched))).collect(Collectors.toList());
        matchedTrip.distanceInM = calculateDistanceInM(matchedTrip.positions);
        matchedTrip.durationInMilliseconds = matchedTrip.positions.get(matchedTrip.positions.size() - 1).timestamp - matchedTrip.positions.get(0).timestamp;
        return matchedTrip;
    }

    public EnrichedTrip(){}

    private static Map<Long, Attributes> buildAttributesMap(List<RouteLink> routeLinks) {
        HashMap<Long, Attributes> map = new HashMap<>();
        for (RouteLink link : routeLinks) {
            map.putIfAbsent(link.linkId, link.attributes);
        }
        return map;
    }

    private static Float calculateDistanceInM(List<EnrichedGpsLocation> positions) {
        Point previous = WayPoint.of(positions.get(0).latitude, positions.get(0).longitude);
        Float distance = (float) 0;
        for (EnrichedGpsLocation position : positions) {
            Point next = WayPoint.of(position.latitude, position.longitude);
            distance += Geoid.WGS84.distance(previous, next).floatValue();
            previous = next;
        }
        return distance;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
