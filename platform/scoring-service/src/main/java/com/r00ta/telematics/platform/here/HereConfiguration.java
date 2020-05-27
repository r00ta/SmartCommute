package com.r00ta.telematics.platform.here;

public class HereConfiguration {
    public final String appKey;
    public final String apiKey;


    public HereConfiguration(String appKey, String apiKey) {
        this.appKey = appKey;
        this.apiKey = apiKey;
    }

    public HereConfiguration() {
        // replace with env variable
        this.appKey = "4Sh8NBaNAIDGOsNB6qBX";
        // replace with env variable
        this.apiKey = "DMrPgLqpQLOiwVrcq2q0LeBPImeVoow4Q9EnNxUvtUU";
    }
}
