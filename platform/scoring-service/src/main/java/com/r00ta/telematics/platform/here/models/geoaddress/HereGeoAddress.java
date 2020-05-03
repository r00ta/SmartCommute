package com.r00ta.telematics.platform.here.models.geoaddress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HereGeoAddress {
    @JsonProperty("Label")
    public String label;

    @JsonProperty("Country")
    public String country;

    @JsonProperty("State")
    public String state;

    @JsonProperty("County")
    public String county;

    @JsonProperty("City")
    public String city;

    @JsonProperty("District")
    public String district;

    @JsonProperty("Street")
    public String street;

    @JsonProperty("HouseNumber")
    public String houseNumber;

    @JsonProperty("PostalCode")
    public String postalCode;
}