package com.r00ta.telematics.platform.users.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {

    @JsonProperty("score")
    public Integer score;

    @JsonProperty("text")
    public String text;

    @JsonProperty("reviewerName")
    public String reviewerName;

    @JsonProperty("isDriverReview")
    public boolean isDriverReview;
}
