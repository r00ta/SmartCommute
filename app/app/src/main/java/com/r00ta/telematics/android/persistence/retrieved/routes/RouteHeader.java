package com.r00ta.telematics.android.persistence.retrieved.routes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.android.network.models.routes.RouteHeaderResponse;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteHeader extends RealmObject {
    @PrimaryKey
    @JsonProperty("routeId")
    public String routeId;

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("days")
    public List<DayOfWeek> days;

    @JsonProperty("startPositionUserLabel")
    public String startPositionUserLabel;

    @JsonProperty("endPositionUserLabel")
    public String endPositionUserLabel;

    @JsonProperty("daysAsDriver")
    public Integer daysAsDriver;

    @JsonProperty("daysAsPassenger")
    public Integer daysAsPassenger;

    public static RouteHeader fromResponse(RouteHeaderResponse response){
        RouteHeader obj = new RouteHeader();
        obj.routeId = response.routeId;
        obj.userId = response.userId;
        obj.days = response.days;
        obj.startPositionUserLabel = response.startPositionUserLabel;
        obj.endPositionUserLabel = response.endPositionUserLabel;
        obj.daysAsDriver = response.daysAsDriver;
        obj.daysAsPassenger = response.daysAsPassenger;

        return obj;
    }
}
