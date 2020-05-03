package com.r00ta.telematics.platform.here.models.geoaddress;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HereGeoAddressView {
    @JsonProperty("Result")
    public List<HereGeoAddressResult> results;
}
