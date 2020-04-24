package com.r00ta.telematics.platform.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class LoadAnalysisRequest {

    @JsonProperty("itemName")
    public String itemName;
}
