package com.r00ta.telematics.platform;

import com.r00ta.telematics.platform.models.AnalyticsRoute;
import org.eclipse.microprofile.config.inject.ConfigProperty;

public class AnalyticsService implements IAnalyticsService {

    @ConfigProperty(name = "greeting.suffix", defaultValue="!")


    @Override
    public void processRoute(AnalyticsRoute route) {

        // upload to ibm storage
    }

    @Override
    public void processAnalysisResults(String resultsURL) {
        // load from ibm storage
    }
}
