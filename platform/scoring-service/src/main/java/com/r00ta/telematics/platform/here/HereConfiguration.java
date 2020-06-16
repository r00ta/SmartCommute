package com.r00ta.telematics.platform.here;

import org.eclipse.microprofile.config.inject.ConfigProperty;

public class HereConfiguration {
    public String appKey;
    public String apiKey;

    public HereConfiguration(String appKey, String apiKey) {
        this.appKey = appKey;
        this.apiKey = apiKey;
    }

    public HereConfiguration() {
    }
}
