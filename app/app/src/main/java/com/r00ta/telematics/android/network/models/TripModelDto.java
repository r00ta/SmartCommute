package com.r00ta.telematics.android.network.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.android.network.models.GpsLocationDto;
import com.r00ta.telematics.android.persistence.models.ObdEngineRpmSample;
import com.r00ta.telematics.android.persistence.models.TripModel;

import java.util.List;
import java.util.stream.Collectors;

public class TripModelDto {

    @JsonProperty("routeId")
    public String routeId;

    @JsonProperty("startTimestamp")
    public Long startTimestamp;

    @JsonProperty("positions")
    public List<GpsLocationDto> positions;

    @JsonProperty("engineRpmSamples")
    public List<ObdEngineRpmSample> engineRpmSamples;

    public TripModelDto(TripModel model){
        this.routeId = model.routeId;
        this.startTimestamp = model.startTimestamp;
        this.positions = model.positions.stream().map(x -> new GpsLocationDto(x)).collect(Collectors.toList());
    }
}
