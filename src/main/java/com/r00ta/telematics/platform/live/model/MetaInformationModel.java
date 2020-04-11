package com.r00ta.telematics.platform.live.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MetaInformationModel {
    @JsonProperty("lastUpdateDocument")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    public Date lastUpdateDocument;

    @JsonProperty("lastUpdate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    public Date lastUpdate;

    public GpsLocation startLocation;

    public GpsLocation endLocation;

    public Long startTimeSession;

    public Long endTimeSession;
}
