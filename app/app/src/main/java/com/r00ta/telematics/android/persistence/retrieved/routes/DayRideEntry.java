package com.r00ta.telematics.android.persistence.retrieved.routes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.DayOfWeek;

import io.realm.RealmObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DayRideEntry extends RealmObject {
    @JsonProperty("day")
    private DayOfWeek day;

    @JsonProperty("ride")
    private DayRouteDrive ride;
}

