package com.r00ta.telematics.platform.enrich.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.SmartDocument;
import com.r00ta.telematics.platform.here.models.routing.RouteMatchModel;
import com.r00ta.telematics.platform.here.models.routing.attributes.Attributes;
import com.r00ta.telematics.platform.here.models.routing.routelinks.RouteLink;
import io.jenetics.jpx.Point;
import io.jenetics.jpx.WayPoint;
import io.jenetics.jpx.geom.Geoid;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrichedTrip extends SmartDocument {

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("tripId")
    public String tripId;

    @JsonProperty("routeId")
    public String routeId;

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

    @JsonProperty("startLocation")
    public String startLocation;

    @JsonProperty("endLocation")
    public String endLocation;

    @JsonProperty("documentVersion")
    public String documentVersion = "1.0";

    public EnrichedTrip() {
    }

    public static EnrichedTrip fromRouteMatch(String userId, String tripId, String routeId, RouteMatchModel routeMatch) {
        Map<Long, Attributes> attributesMap = buildAttributesMap(routeMatch.routeLinks);
        EnrichedTrip matchedTrip = new EnrichedTrip();
        matchedTrip.tripId = tripId;
        matchedTrip.userId = userId;
        matchedTrip.routeId = routeId;
        matchedTrip.startTimestamp = routeMatch.tracePoints.get(0).timestamp;
        matchedTrip.positions = routeMatch.tracePoints.stream().map(x -> new EnrichedGpsLocation(x, attributesMap.get(x.linkIdMatched))).collect(Collectors.toList());
        matchedTrip.distanceInM = calculateDistanceInM(matchedTrip.positions);
        matchedTrip.durationInMilliseconds = matchedTrip.positions.get(matchedTrip.positions.size() - 1).timestamp - matchedTrip.positions.get(0).timestamp;
        return matchedTrip;
    }

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

        System.out.println(distance);
        return distance;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
