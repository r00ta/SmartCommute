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


// price
// https://fleet.cit.api.here.com/2/calculateroute.json?jsonAttributes=41&waypoint0=38.72639,-9.14949&detail=1&waypoint1=47.54881,7.58782&routelegattributes=li&routeattributes=gr&maneuverattributes=none&linkattributes=none,rt,fl&legattributes=none,li,sm&currency=EUR&departure=&tollVehicleType=2&trailerType=0&trailersCount=0&vehicleNumberAxles=2&trailerNumberAxles=0&hybrid=0&emissionType=5&fuelType=petrol&height=1.67m&trailerHeight=0&vehicleWeight=1739&limitedWeight=1.739t&disabledEquipped=0&minimalPollution=0&hov=0&passengersCount=2&tiresCount=4&commercial=0&heightAbove1stAxle=1m&width=1.8&length=4.41&mode=fastest;car;traffic:disabled&rollups=none,country;tollsys&alternatives=1&app_id=inCUge3uprAQEtRaruyaZ8&app_code=9Vyk_MElhgPCytA7z3iuPA&jsoncallback=parseRoutingResponse