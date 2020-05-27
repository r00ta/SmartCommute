package com.r00ta.telematics.platform.routes.models;

public enum MatchingPendingStatus {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    DECLINED("DECLINED");

    private String status;

    MatchingPendingStatus(String status) {
        this.status = status;
    }
}
