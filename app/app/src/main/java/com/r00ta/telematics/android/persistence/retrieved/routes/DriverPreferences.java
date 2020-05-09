package com.r00ta.telematics.android.persistence.retrieved.routes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.realm.RealmObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverPreferences extends RealmObject {

    @JsonProperty("availableAsDriver")
    public boolean availableAsDriver;

    /**
     * 0 if not available
     */
    @JsonProperty("radiusStartPoint")
    public Float radiusStartPoint;

    /**
     * 0 if not available
     */
    @JsonProperty("onTheRouteOption")
    public boolean onTheRouteOption;

    /**
     * Flexibility in time?
     */
    @JsonProperty("flexibility")
    public Long flexibility;
}
