package com.r00ta.telematics.models.routes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum MatchingPendingStatus {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    DECLINED("DECLINED");

    private String status;

    MatchingPendingStatus(String status) {
        this.status = status;
    }
}