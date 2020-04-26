package com.r00ta.telematics.models.livetrip;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ObdEngineRpmSample {

    @JsonProperty("timestamp")
    public Long timestmap;

    @JsonProperty("value")
    public Long value;
}
