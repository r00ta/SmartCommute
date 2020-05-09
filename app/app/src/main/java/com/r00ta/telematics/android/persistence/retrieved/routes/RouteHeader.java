package com.r00ta.telematics.android.persistence.retrieved.routes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.android.network.models.routes.RouteHeaderResponse;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteHeader extends RealmObject {
    @PrimaryKey
    public Long id;

    @JsonProperty("routeId")
    public String routeId;

    @JsonProperty("userId")
    public String userId;

    @JsonProperty("days")
    public RealmList<String> days;

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
        obj.id = UUID.nameUUIDFromBytes(response.routeId.getBytes()).getMostSignificantBits();
        obj.routeId = response.routeId;
        obj.userId = response.userId;
        obj.days = new RealmList<String>();
        obj.days.addAll(response.days.stream().map(x -> x.toString()).collect(Collectors.toList()));
        obj.startPositionUserLabel = response.startPositionUserLabel;
        obj.endPositionUserLabel = response.endPositionUserLabel;
        obj.daysAsDriver = response.daysAsDriver;
        obj.daysAsPassenger = response.daysAsPassenger;

        return obj;
    }
}
