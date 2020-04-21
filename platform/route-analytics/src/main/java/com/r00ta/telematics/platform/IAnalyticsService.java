package com.r00ta.telematics.platform;

import com.r00ta.telematics.platform.models.AnalyticsRoute;

public interface IAnalyticsService {
    void processRoute(AnalyticsRoute route);

    void processAnalysisResults(String resultsURL);
}
