package com.r00ta.telematics.platform.here.models.geoaddress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HereGeoAddressResult {

    @JsonProperty("Distance")
    public Float distance;

    @JsonProperty("Location")
    public HereGeoAddressLocation location;
}