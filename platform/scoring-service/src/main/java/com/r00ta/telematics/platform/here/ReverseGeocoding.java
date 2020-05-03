package com.r00ta.telematics.platform.here;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r00ta.telematics.platform.enrich.models.GpsLocation;
import com.r00ta.telematics.platform.here.models.geoaddress.HereGeoAddress;
import com.r00ta.telematics.platform.here.models.geoaddress.HereGeoAddressResult;
import com.r00ta.telematics.platform.here.models.geoaddress.ReverseGeocodeTopResponse;
import com.r00ta.telematics.platform.utils.HttpHelper;

public class ReverseGeocoding {

    private static ObjectMapper MAPPER = new ObjectMapper();
    private final HereConfiguration configuration;
    private final String hereBaseHost = "https://reverse.geocoder.ls.hereapi.com/6.2/";
    private final HttpHelper httpHelper = new HttpHelper(hereBaseHost);

    public ReverseGeocoding(HereConfiguration configuration) {
        this.configuration = configuration;
    }

    public Optional<HereGeoAddress> getAddressFromLocation(GpsLocation location) {
        String response = httpHelper.doGet(String.format("reversegeocode.json?apiKey=%s&prox=%s,%s,%s&maxresults=1&mode=retrieveAddress", configuration.apiKey, location.latitude, location.longitude, 250));
        try {
            System.out.println(response);
            ReverseGeocodeTopResponse topResponse = MAPPER.readValue(response, ReverseGeocodeTopResponse.class);
            if (topResponse.response.views.isEmpty() || topResponse.response.views.get(0).results.isEmpty()){
                return null;
            }

            // TODO: get best result based on distance;
            return Optional.of(topResponse.response.views.get(0).results.get(0).location.address);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("not a valid route match", e);
        }
    }
}