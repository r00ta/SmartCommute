package com.r00ta.telematics.models.routes;

public enum MatchingPendingStatus {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    DECLINED("DECLINED");

    private String status;

    MatchingPendingStatus(String status) {
        this.status = status;
    }
}