package com.r00ta.telematics.platform.here;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.platform.enrich.models.TripModel;
import com.r00ta.telematics.platform.here.models.RouteMatchModel;
import com.r00ta.telematics.platform.utils.HttpHelper;

public class RouteMatching {

    private static ObjectMapper MAPPER = new ObjectMapper();
    private final String appKey;
    private final String apiKey;
    private final String hereBaseHost = "https://m.fleet.ls.hereapi.com/2/";
    private final HttpHelper httpHelper = new HttpHelper(hereBaseHost);

    public RouteMatching(String appKey, String apiKey) {
        this.appKey = appKey;
        this.apiKey = apiKey;
    }

    public RouteMatching() {
        this.appKey = "4Sh8NBaNAIDGOsNB6qBX";
        this.apiKey = "DMrPgLqpQLOiwVrcq2q0LeBPImeVoow4Q9EnNxUvtUU";
    }

    public RouteMatchModel calculateRouteMatching(TripModel trip) {
        String gpx = GpxFactory.getGpxAsString(trip);
        System.out.println(gpx);
        String response = httpHelper.doPost(String.format("matchroute.json?apiKey=%s&routemode=car&attributes=SPEED_LIMITS_FCn(FROM_REF_SPEED_LIMIT,TO_REF_SPEED_LIMIT)", apiKey), gpx);
        System.out.println(response);
        try {
            return MAPPER.readValue(response, RouteMatchModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("not a valid route match", e);
        }
    }
}
