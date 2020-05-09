package com.r00ta.telematics.android.network.models.trips;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.android.persistence.models.trips.GpsLocation;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GpsLocationDto {
    @JsonProperty("timestamp")
    public Long timestamp;

    @JsonProperty("latitude")
    public Double latitude;

    @JsonProperty("longitude")
    public Double longitude;

    @JsonProperty("accuracy")
    public float accuracy;

    @JsonProperty("speed")
    public Float speed;

    @JsonProperty("bearing")
    public Float bearing;

    @JsonProperty("elevation")
    public double elevation;

    public GpsLocationDto() {
    }

    public GpsLocationDto(GpsLocation location){
        this.timestamp = location.timestamp;
        this.latitude = location.latitude;
        this.longitude = location.longitude;
        this.accuracy = location.accuracy;
        this.speed = location.speed;
        this.bearing = location.bearing;
        this.elevation = location.elevation;
    }
}