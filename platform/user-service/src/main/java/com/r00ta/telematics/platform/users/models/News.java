package com.r00ta.telematics.platform.users.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.telematics.platform.SmartDocument;

public class News extends SmartDocument {
    @JsonProperty("text")
    public String text;

    public News(){}

    public News(String text){
        this.text = text;
    }
}
