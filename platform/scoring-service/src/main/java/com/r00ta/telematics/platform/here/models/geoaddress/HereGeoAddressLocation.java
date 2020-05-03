package com.r00ta.telematics.platform.here.models.geoaddress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HereGeoAddressLocation {
    @JsonProperty("Address")
    public HereGeoAddress address;
}
